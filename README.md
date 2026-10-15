# Java Learning

哈尔滨工业大学 2026 级 · 大数据管理与应用
自学 Java 的练习与项目代码。目标方向：**大数据开发 / 后端开发**。

## 目录

| 路径 | 内容 |
|---|---|
| `src/com/example/test/` | Java SE 基础练习（面向对象、继承、多态、抽象类、封装校验） |
| `src/com/example/library/` | **P1 图书管理系统**（✅ 已完成，含边界测试） |
| `docs/` | 学习计划与设计文档 |

## 项目

### P1 图书管理系统（控制台应用）

**状态**：✅ 已完成（边界测试 50 断言全通过）

命令行图书管理系统。目标是把「类设计 → 继承与多态 → 自定义异常 → 集合框架 → 菜单交互」这条链路走通，并在过程中建立"改代码后能立刻知道有没有搞坏东西"的回归测试习惯。

**运行方式**

IDEA：运行 `com.example.library.Main`

命令行：

```bash
javac -encoding UTF-8 -d out src/com/example/library/*.java src/com/example/library/exception/*.java
java -Dfile.encoding=UTF-8 -cp out com.example.library.Main
```

**类结构**

| 类 | 职责 |
|---|---|
| `Book` | 书目：id、isbn、书名、作者、是否可借 |
| `User`（抽象类） | 用户公共字段 + 抽象方法 `getRoleName()` |
| `Reader` / `Admin` | 读者 / 管理员，区别在角色名与权限 |
| `Loan` | 一条借阅记录：书、人、借出日、应还日 |
| `Library` | 核心逻辑：藏书的增删查、借还、用户管理 |
| `SampleData` | 预置演示数据 |
| `Main` | 唯一入口：菜单、登录、输入输出 |
| `exception` 包 | `BookNotFoundException`、`UserNotFoundException` |

**已实现功能**

- 读者：注册、登录、浏览书目、借书、还书、查看自己的在借记录
- 管理员：额外可添加/删除书籍、查询/删除用户
- 借阅额度：每个用户有同时在借上限，归还后额度释放
- 异常处理：书/用户不存在时提示错误而非崩溃；菜单非法输入自动重试

**设计要点**

*1. 为什么 `User` 是抽象类*

系统里不该存在"普通的用户"——一个人要么是读者、要么是管理员。抽象类把这条业务事实写进了类型系统：`new User(...)` 直接编译不过。区别只体现在 `getRoleName()` 上，`User.toString()` 靠它输出角色名，**父类里零 `instanceof`**。

*2. 权限判断集中在 `Library`，不在 `Main`*

`Main` 只做"读输入 → 调方法 → 打印结果"，不出现任何业务判断。权限检查放在 `Library.deleteBook` / `deleteUser` 内，且**授权检查必须排在查存在性之前**——否则未授权用户可以从"抛异常 / 返回 false"的差异推断出系统里有哪些 id。

*3. 自定义异常 vs 返回 `false` 的边界*

| 情况 | 表达方式 | 理由 |
|---|---|---|
| id 不存在 | 抛 `BookNotFoundException` | 调用方传了系统里没有的 id，属于传参错误，快速失败 |
| 书已被借走、无权限、超额 | 返回 `boolean false` | "正常但少见的结果"，调用方需要处理，不是编程错误 |

异常继承 `RuntimeException`（非受检），不强制每个调用方 `try-catch`；`Main` 在最外层按会话统一捕获。

*4. 集合重构：数组 → `ArrayList` + `HashMap` 索引*

**重构前**：三个定长数组 + 三个计数器（`bookCount` 兼作"下一个空位下标"）。问题：容量写死、删除要手写元素前移（极易漏 `count--`）、`findBookById` 是线性查找 O(n)。

**重构后**：

```java
private final List<Book> books = new ArrayList<>();
private final List<User> users = new ArrayList<>();
private final List<Loan> loans = new ArrayList<>();
private final Map<String, Book> bookIndex = new HashMap<>();   // id → Book
private final Map<String, User> userIndex = new HashMap<>();   // id → User
```

- `findBookById` / `findUserById` 从 **O(n) 降为 O(1)**
- 容量、扩容、删除、前移全部交给 `ArrayList`

**代价**：出现了两份数据（`List` 存顺序、`Map` 存索引），必须同步维护——`addBook` / `deleteBook` 里两个结构成对操作，这条约束在代码中以注释标注。

*5. `Book.equals` / `hashCode` 只用身份字段*

```java
@Override public boolean equals(Object o) { /* 比较 isbn, id, title, author */ }
@Override public int hashCode() { return Objects.hash(isbn, id, title, author); }
```

`available`（可借状态）**被刻意排除**：同一本书借出、归还之后仍是同一本书，身份不该随状态漂移。同时也保证 `equals` 用到的字段集与 `hashCode` 完全一致——两者不一致会让对象放进 `HashSet` 后 `contains()` 找不到。

**踩过的坑（真实记录）**

> 这一节是本项目最有价值的部分。每条都是实际写出来、跑出来的 bug。

*坑 1｜构造器参数顺序不一致，编译器完全不报错*

`Book` 构造器声明为 `(isbn, id, title, author)`，调用方按 `(id, isbn, ...)` 传。两个参数都是 `String`，编译器无法报错，运行时表现为：书明明加进去了，`findBookById("B001")` 却永远抛异常。

对策：主键放第一位靠位置区分；打开 IDEA 的 `Inlay Hints → Parameter names`。**遇到"数据明明加了却查不到"，第一件事是打印字段实际值，而不是怀疑查找逻辑。**

*坑 2｜`static` 误用在计数上*

`Library` 的计数一度写成 `static`，导致两个 `Library` 实例共享计数——新建一个图书馆会把另一个的计数清零，测试之间互相污染。

判据：数据"每个对象各自一份"→ 实例字段；"全系统共用一份"→ `static`。

*坑 3｜权限判断写成 `!(A || B)`，任意读者可删书*

```java
// ❌ 错误版本
if (!(operator instanceof Admin || !bookIndex.containsKey(bookId))) {
    return false;
}
```

按德摩根律 `!(A || B)` = `!A && !B`，即"既没权限、书又不存在"才拒绝。结果是**读者删一本存在的书 → 条件为 false → 不进入 if → 直接删掉并返回 `true`**。

更麻烦的是它被一条**假通过的测试**掩盖了：测试里"读者删书"确实返回了 `false`，但原因是"书不存在"那条分支，不是权限。

修正：一个条件管一件事，拆成独立 `if`；**授权 → 存在性 → 执行**。

教训：嵌套否定（`!(A || B)`）一律拆成两句；**测"拒绝"必须同时断言"状态没变"**——只断言返回 `false` 会漏掉"返回值说拒绝了、数据其实已改"的情况。

*坑 4｜`Book.equals` 包含可变字段 `available`*

`available` 是 `Book` 里唯一会变的字段。把它算进判等的后果：同一本书借出前后互不相等；而 `returnBook` 靠 `l.getBook().equals(book)` 匹配记录、`deleteBook` 靠 `books.remove(book)` 定位元素，都依赖判等——一旦"内容相同的不同对象"参与匹配，逻辑就会静默失效。

修正：`equals`/`hashCode` 只保留身份字段。**原则：只用身份字段，不用状态字段。**

*其它已修正的小问题*

| 问题 | 修正 |
|---|---|
| `Book` 构造器的异常消息写反 | 消息必须与事实一致，否则排查时被引向错误方向 |
| `Main.register` 失败提示"用户已满" | `addUser` 返回 `false` 的唯一原因是 id 重复，改为"该 id 已存在" |
| `id.equals(book.getId())` 在 id 为 `null` 时抛 NPE | 用 `Objects.equals(a, b)` |
| `deleteBook` / `deleteUser` 授权顺序不一致 | 统一为"授权 → 存在性 → 执行" |

**测试**

| 测试类 | 内容 |
|---|---|
| `LookupTest` | 查找优化验收：基本增删查、重复 id、删除后查找、借还回归、异常类型 |
| `BoundaryTest` | 边界与契约测试，10 组 50 个断言 |

`BoundaryTest` 覆盖的性质：构造器输入边界（`null` / `""` / `"   "`）｜构造失败不污染库｜`equals` 与 `hashCode` 字段集一致｜借还全链路状态归位｜**孪生对象**（内容相同、不同引用）能走通判等匹配｜删除权限矩阵（含状态快照）｜限额失败后其余状态不变｜**失败操作的原子性**｜借还 10 轮后计数不漂移。

```bash
java -Dfile.encoding=UTF-8 -cp out com.example.library.BoundaryTest
```

**已知限制（后续改进方向）**

- 数据存在内存中，**程序退出即丢失** —— P2 用文件持久化解决
- `getActiveLoans` / `getActiveBooks` / `getActiveUsers` 三段代码形状重复（建数组 → 按下标拷贝）—— 学习泛型后抽成泛型方法，或直接返回 `List`
- 未使用接口 —— 留到 P2：CSV / JSON 两种写入器实现同一接口才是真实用例
- `Book.available` 用 `boolean`，只能表达"可借/不可借" —— 引入枚举类支持"遗失""下架"
- 一个 `Book` 对象代表"一种书"，不支持同书多副本
- 借阅记录只保留"当前在借"，没有历史记录
- `loanDays`（借期 30 天）写在代码里，未抽成配置
- 无并发控制（非线程安全）

## 进度

| 阶段 | 内容 | 状态 |
|---|---|---|
| W1 | 面向对象：类 / 构造器 / 方法重载 / 继承 / 多态 / 抽象类 / 接口 | ✅ 完成 |
| W2 | P1 图书管理系统（数据类 + `Library` + 菜单 + 权限分流） | ✅ 完成 |
| W3 | 集合框架（`List` / `Set` / `Map`）→ 用 `ArrayList` + `HashMap` 重写 `Library` | ✅ 完成 |
| W4 | 边界测试与缺陷修复（权限绕过、判等契约） | ✅ 完成 |
| W5 | README + 推 GitHub；IO 文件读写 → **P2 记账管理（数据持久化）** | 🔄 进行中 |

## 环境

- JDK 21
- IntelliJ IDEA

## 学习记录

- **W1（2026-09-14 ~ 09-20）**：`com.example.test` 下的练习代码，包含封装校验、继承与方法重写、多态与向上转型、用抽象类消除 `instanceof`，以及 `Bank` / `BankDemo` 演示
- **W2（2026-09-21 起）**：开始 P1 图书管理系统；补 `static`、包与可见性、异常处理、`equals` / `hashCode`
- **W3（2026-10 上旬）**：集合框架；`Library` 由数组重构为 `ArrayList` + `HashMap` 索引
- **W4（2026-10-14）**：`BoundaryTest` 边界测试 50 断言全通过；修复 `deleteBook` 权限绕过、`Book.equals` 可变字段、授权顺序不一致
