# Student Management System — Java Full-Stack Crash Project

A complete CRUD app covering every keyword in your interview:
**Java · JDBC · Servlet · JSP** (classic MVC) plus a **React** frontend.

## The big picture (memorize this)

```
CLASSIC PATH (Java + JDBC + Servlet + JSP):
  Browser  ─►  StudentServlet (Controller)  ─►  StudentDAO (JDBC)  ─►  MySQL
     ▲                                                                   │
     └──────────  JSP page (View, HTML)  ◄─── data (Model) ◄────────────┘

REACT PATH (bonus — same backend, JSON instead of HTML):
  React app  ─►  StudentApiServlet (/api/students, JSON)  ─►  StudentDAO  ─►  MySQL
```

One sentence: **the Servlet is the traffic controller, the DAO does the SQL over
JDBC, MySQL stores the data, and JSP (or React) shows it.** That's full-stack.

## Files, by layer

| Layer | File | Role |
|-------|------|------|
| Model | `src/main/java/com/jay/sms/model/Student.java` | one table row as a Java object |
| DB util | `.../util/DBConnection.java` | opens the JDBC connection |
| DAO (JDBC) | `.../dao/StudentDAO.java` | **all the SQL lives here** |
| Controller | `.../controller/StudentServlet.java` | handles /students, picks the JSP |
| REST API | `.../controller/StudentApiServlet.java` | /api/students, returns JSON for React |
| View (JSP) | `src/main/webapp/WEB-INF/views/*.jsp` | the HTML pages |
| Frontend | `react-frontend/src/App.jsx` | the React UI |
| Schema | `db/schema.sql` | creates the database + table |

## Setup — already done on this machine ✅

I installed and wired everything up for you tonight. On THIS computer:
- **Java 17** (Temurin) — at `C:\Program Files\Eclipse Adoptium\jdk-17.0.20.101-hotspot`
- **Maven 3.9.16** — at `C:\Users\Perfect\Downloads\apache-maven-3.9.16-bin\...`
- **Database** — you already had **XAMPP MySQL/MariaDB** running on port 3306,
  root user with an **empty password**. I created the `studentdb` database and
  `students` table and added 3 sample rows.
- `DBConnection.java` is set to that empty password, so it just works.

> Make sure **XAMPP's MySQL is started** (open XAMPP Control Panel → Start MySQL)
> before you run the app. That's the only manual thing.

## Run the classic app (Java + JDBC + Servlet + JSP)

**Easiest:** double-click **`run.bat`** in this folder (it sets Java + Maven for
you). Or in a terminal from the project root:
```bash
./run.bat
```
Wait for **`Started Jetty Server`**, then open **http://localhost:8080** — you'll
see the 3 students. Add / Edit / Delete all work. Stop with `Ctrl+C`.

<!-- kept for reference: the raw command run.bat executes -->
<!-- mvn jetty:run  (with JAVA_HOME pointing at the JDK 17 above) -->

## Run the React frontend (bonus)

Keep the backend running, then in a SECOND terminal:
```bash
cd react-frontend
npm install
npm run dev
```
Open **http://localhost:5173/** — same data, React UI calling `/api/students`.

## Tonight's plan (don't just read — TYPE it and RUN it)

You learn this by getting it running and breaking it, not by reading. Order:

1. **Setup + run (1–1.5 hrs).** Install JDK/Maven/MySQL, run `mvn jetty:run`,
   see the app work in the browser. Getting it RUNNING is 50% of the battle.
2. **Trace one request (1 hr).** Click "Add Student" and follow the path in the
   code: `list.jsp` link → `StudentServlet.doGet` → `form.jsp` → submit →
   `doPost` → `StudentDAO.insert` → MySQL → redirect → list. Say it out loud.
3. **Read `StudentDAO.java` until you can write `insert` from memory (1 hr).**
   The JDBC 5-step pattern is the single most-asked thing. Practice it.
4. **JSP + Servlet (45 min).** Understand: JSP = HTML + `${}`/`<c:forEach>`;
   servlet = Java class that receives the request and forwards to a JSP.
5. **React (1 hr).** Run the frontend. Read `App.jsx`. Learn `useState`,
   `useEffect`, `fetch`, and `.map()` to render a list.
6. **Sleep 4–5 hrs.** Tired-brain forgets. A rested run-through tomorrow morning
   beats an all-nighter. Do one more full run + explain-out-loud before you go.

Prioritize: if short on time, **the classic Java/JDBC/JSP path is the priority**
(it matches the keywords). React is the bonus that impresses.

## If something breaks

- `mvn` not found → PATH not set / reopen terminal. `java` not found → install JDK.
- `Communications link failure` / `Access denied` → MySQL not running, or wrong
  password in `DBConnection.java`, or you skipped `schema.sql`.
- `Unknown database 'studentdb'` → run `mysql -u root -p < db/schema.sql`.
- React shows empty table → the backend must be running on 8080 at the same time.
- Port 8080 busy → stop other servers, or change the Jetty port in `pom.xml`.

## Interview cheat-sheet (answer these out loud tonight)

**Core / architecture**
- *What is full-stack?* Frontend (what the user sees — JSP/React), backend (server
  logic — Java servlets), and database (MySQL). I built all three layers.
- *What is MVC?* Model (data — `Student`), View (UI — JSP), Controller (routing —
  `StudentServlet`). Separates concerns so each layer has one job.

**JDBC**
- *What is JDBC?* Java Database Connectivity — the standard Java API to run SQL
  against a database. My `StudentDAO` uses it.
- *The 5 steps?* 1) get a `Connection`, 2) create a `PreparedStatement` with SQL,
  3) set the `?` parameters, 4) `executeQuery()` (SELECT) or `executeUpdate()`
  (INSERT/UPDATE/DELETE), 5) read the `ResultSet` and close everything.
- *Statement vs PreparedStatement?* PreparedStatement uses `?` placeholders —
  it's precompiled, faster on reuse, and **prevents SQL injection**. Always prefer it.
- *What is a `ResultSet`?* The table of rows a SELECT returns; you loop it with
  `while (rs.next())` and read columns with `rs.getString("name")`.
- *`executeQuery` vs `executeUpdate`?* Query returns a ResultSet (SELECT);
  Update returns an int row-count (INSERT/UPDATE/DELETE).

**Servlet**
- *What is a servlet?* A Java class (extends `HttpServlet`) that runs on the server
  and handles HTTP requests via `doGet`/`doPost`.
- *`@WebServlet`?* An annotation that maps a URL to the servlet (replaces the old
  web.xml `<servlet-mapping>`).
- *forward vs redirect?* `forward` = server-side, same request, keeps data (used to
  hand data to a JSP). `redirect` = tells the browser to make a NEW request (used
  after a POST so refresh doesn't re-submit).

**JSP**
- *What is JSP?* JavaServer Pages — HTML with dynamic Java-driven content. It's
  compiled into a servlet by the server. Used for the View layer.
- *What is `${student.name}`?* Expression Language (EL) — it calls `getName()` on
  the `student` object the servlet placed in the request. No Java code needed.
- *What is JSTL?* A tag library: `<c:forEach>` to loop, `<c:if>` for conditions —
  so you avoid raw Java scriptlets (`<% %>`) inside the page.
- *Why keep JSPs in `WEB-INF`?* Files there can't be opened directly by URL — they
  can only be reached via a servlet forward. That enforces the MVC flow.

**React**
- *What is React?* A JavaScript library for building UIs from reusable components.
- *What is a component?* A function that returns JSX (HTML-like markup). `App` is one.
- *What is state? `useState`?* State is data that can change and re-renders the UI
  when it does. `useState` creates it: `const [x, setX] = useState(initial)`.
- *`useEffect`?* Runs side-effects (like fetching data) after render; `[]` deps =
  run once on load. That's how I load students from the backend.
- *How does React talk to Java?* Over HTTP with `fetch()` to `/api/students`; the
  servlet returns JSON, React renders it. Frontend and backend are decoupled.
- *JSP vs React?* JSP renders HTML on the **server** (page reloads). React renders
  on the **client** (updates without full reload — a single-page app).

**Likely "can you code it?" ask:** they may hand you a keyboard. Practice writing
`StudentDAO.insert()` and a basic `doGet` from a blank file until it's muscle memory.

## Honest note
You won't master 4 technologies in one night — nobody does. But you CAN get this
project running, understand the flow, and speak to every keyword confidently.
That, plus honesty about what you're still learning, is a strong junior showing.
Good luck. 🚀
