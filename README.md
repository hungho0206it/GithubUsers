# GithubUsers 👥

A **Clean Architecture** Android sample that demonstrates how to browse public GitHub users, cache them locally, and page through the list smoothly — built with **100 % Kotlin**.

---
## ✨ Features
- Fetches users from the public **GitHub REST API** and shows avatar, login, and profile link
- Infinite scrolling via **Paging 3** + `RemoteMediator`, including retry / refresh UI states
- **Offline‑first** – last successful page is stored in **Room** (encrypted with SQLCipher) and served when offline
- **Koin** for dependency‑injection across modules
- Image loading & caching with **Glide**
- Modern UI: ViewBinding, Navigation Component, Material 3 widgets
- Two flavours: `dev` (debuggable, `*.dev` idSuffix) & `prod`
- 90 %+ unit‑test coverage enforced by **Kover** (JaCoCo wrapper)

---
## 🏗 Tech stack
| Layer        | Libraries & Tools                                                                                              |
|--------------|-----------------------------------------------------------------------------------------------------------------|
| **UI**       | AndroidX, Material Components, SwipeRefreshLayout, Navigation‑Fragment                                          |
| **DI**       | Koin                                                                   |
| **Async**    | Kotlin Coroutines & Flow                                                                                        |
| **Images**   | Glide                                                                                                           |
| **Network**  | Retrofit 2, OkHttp 3, Gson converter                                                                            |
| **Database** | Room + Paging, SQLCipher                                                                                        |
| **Testing**  | JUnit 4, MockK, Coroutine Test, Room Test, MockWebServer, Robolectric, Espresso                                 |
| **Coverage** | Kover plugin (JaCoCo)                                                                                           |
| **Build**    | Gradle KTS (Java 11 toolchain, Compile/Target SDK 35)                                                           |

_Exact versions are centralised in **`libs.versions.toml`**._

---
## 🗂 Modules
| Module | Purpose |
|--------|---------|
| **:app**    | Presentation layer & UI glue |
| **:domain** | Business logic, use‑cases, models |
| **:data**   | Retrofit, Room, repository implementations |

---
## 🗺 Architecture
```text
presentation (:app)  <--Koin-->  domain (:domain)  <--interface-->  data (:data)
     | ViewBinding              | UseCases                        | RepositoryImpl
     | ViewModel                | Models                          | Retrofit, Room
     | PagingData<UIModel>      | Repository                      | RemoteMediator
```
* **Unidirectional data‑flow**  
  `UI → ViewModel intents → UseCases → Repository → DB/Net → ViewModel state → UI`

---
## 🚀 Getting started
```bash
git clone https://github.com/hungho0206it/GithubUsers.git
cd GithubUsers
./gradlew :app:installDevDebug   # run dev flavour on a connected device
```
> **Requirements**: JDK 11 & Android Studio Iguana | Minimum SDK 24

### Optional – Personal token
Unauthenticated GitHub calls work, but if you hit rate‑limits you can add
```properties
GITHUB_TOKEN=ghp_********************************
```
to `local.properties` and attach it in an `OkHttp` interceptor.

---
## 🧪 Tests & coverage
```bash
./gradlew koverHtmlReport                 # unit tests with coverage
```
HTML reports: `app/build/reports/kover/html/index.html`

---
## 📸 Screenshots
| List | User Detail |
|------|-----------------|
| ![Screenshot_1746950865](https://github.com/user-attachments/assets/4af631ec-3eb6-49a2-9c19-c9e9edecfc9c) | ![Screenshot_1746950867](https://github.com/user-attachments/assets/862f2262-b336-4b56-a36a-84acf462aa8f)
