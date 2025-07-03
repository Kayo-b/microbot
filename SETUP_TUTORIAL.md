## Quick Start for Windows Users

If you already have Maven installed in VS Code, here's the fastest way to get started:

**️IMPORTANT: First check your Java version!**
```powershell
java -version
```
You need Java 17 or higher. If you have Java 11 or older, upgrade first (see detailed instructions below).

**IMPORTANT: Check if Maven is available in your terminal!**
```powershell
mvn -version
```
If this fails, you need to install Maven system-wide (see detailed instructions below).

**Once you have Java 17+ and Maven working:**

1. **Open your PowerShell terminal in VS Code** (Ctrl + ` or Terminal → New Terminal)
2. **Navigate to your project directory:**
   ```powershell
   cd "c:\Users\kyobh\projects\microbot"
   ```
3. **Build the project:**
   ```powershell
   mvn clean package -DskipTests
   ```
4. **Run the application:**
   ```powershell
   cd runelite-client\target; java -jar microbot-1.9.2.jar
   ```

If you encounter any issues, follow the detailed setup instructions below.

## Prerequisites

1. **Java 17 or higher** - Required for the project to compile properly
2. **Maven** - Build tool for managing dependencies and compilation
3. **Git** - For cloning the repository (if needed)

## Step 1: Install Required Software

### Windows Setup

#### Install Java 17 (OpenJDK)
1. **Download and install Java 17:**
   - Go to [Adoptium](https://adoptium.net/) or [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
   - Download Java 17 for Windows
   - Run the installer and follow the setup wizard
   - Make sure to check "Add to PATH" during installation

2. **Verify Java installation:**
   Open PowerShell or Command Prompt and run:
   ```powershell
   java -version
   javac -version
   ```

#### Install Maven
1. **Option 1: Using Chocolatey (Recommended)**
   ```powershell
   # Install Chocolatey first (if not already installed)
   Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
   
   # Install Maven
   choco install maven
   ```

2. **Option 2: Manual Installation**
   - Download Maven from [Apache Maven](https://maven.apache.org/download.cgi)
   - Extract to `C:\Program Files\Apache\maven`
   - Add `C:\Program Files\Apache\maven\bin` to your PATH environment variable
   - Set `MAVEN_HOME` environment variable to `C:\Program Files\Apache\maven`

3. **Verify Maven installation:**
   ```powershell
   mvn -version
   ```

#### VS Code Extension Setup
Since you have Maven installed in VS Code, make sure you have these extensions:
- **Extension Pack for Java** (includes Maven for Java)
- **Java Extension Pack**

### Linux/Ubuntu Setup

#### Install Java 17 (OpenJDK)
```bash
# Update package list
sudo apt update

# Install OpenJDK 17
sudo apt install openjdk-17-jdk

# Verify installation
java -version
javac -version
```

#### Install Maven
```bash
# Install Maven
sudo apt install maven

# Verify installation
mvn -version
```

## Step 2: Clone or Download the Project

If you haven't already, clone the Microbot repository:
```bash
git clone <repository-url>
cd Microbot
```

## Step 3: Build the Project

### Windows Commands

#### Clean and compile the entire project:
```powershell
mvn clean compile
```

#### Build the JAR file with all dependencies:
```powershell
mvn clean package
```

This will create the executable JAR file in:
`runelite-client\target\microbot-1.9.2.jar`

### Linux Commands

#### Clean and compile the entire project:
```bash
mvn clean compile
```

#### Build the JAR file with all dependencies:
```bash
mvn clean package
```

This will create the executable JAR file in:
`runelite-client/target/microbot-1.9.2.jar`

## Step 4: Development Workflow

### Making Changes to Scripts

1. **Edit your script files** (e.g., `ExampleScript.java`, `ExamplePlugin.java`)
2. **Rebuild the project** after making changes:
   
   **Windows:**
   ```powershell
   mvn clean package
   ```
   
   **Linux:**
   ```bash
   mvn clean package
   ```

3. **Run the updated JAR**:
   
   **Windows:**
   ```powershell
   cd runelite-client\target
   java -jar microbot-1.9.2.jar
   ```
   
   **Linux:**
   ```bash
   cd runelite-client/target
   java -jar microbot-1.9.2.jar
   ```

### Quick Build (without tests)
If you want faster builds during development:

**Windows:**
```powershell
mvn clean package -DskipTests
```

**Linux:**
```bash
mvn clean package -DskipTests
```

### Build only the client module

**Windows:**
```powershell
mvn clean package -pl runelite-client -am
```

**Linux:**
```bash
mvn clean package -pl runelite-client -am
```

## Step 5: Running the Application

### On Windows:
```powershell
cd runelite-client\target
java -jar microbot-1.9.2.jar
```

### On Desktop Ubuntu (with GUI):
```bash
cd runelite-client/target
java -jar microbot-1.9.2.jar
```

### Common run options:

**Windows:**
```powershell
# Show help
java -jar microbot-1.9.2.jar --help

# Run in safe mode (disables external plugins)
java -jar microbot-1.9.2.jar --safe-mode

# Run in developer mode
java -jar microbot-1.9.2.jar --developer-mode

# Enable debug output
java -jar microbot-1.9.2.jar --debug
```

**Linux:**
```bash
# Show help
java -jar microbot-1.9.2.jar --help

# Run in safe mode (disables external plugins)
java -jar microbot-1.9.2.jar --safe-mode

# Run in developer mode
java -jar microbot-1.9.2.jar --developer-mode

# Enable debug output
java -jar microbot-1.9.2.jar --debug
```

## Project Structure Overview

```
Microbot/
├── runelite-client/          # Main client code
│   ├── src/main/java/        # Java source files
│   │   └── net/runelite/client/plugins/microbot/
│   │       ├── example/      # Example plugin and scripts
│   │       └── ...           # Other microbot plugins
│   └── target/               # Build output
│       └── microbot-1.9.2.jar # Executable JAR
├── runelite-api/             # RuneLite API
├── cache/                    # Cache module
├── pom.xml                   # Maven configuration
└── ...
```

## Creating Your Own Scripts

1. **Copy the example plugin** as a template:
   ```bash
   cp -r runelite-client/src/main/java/net/runelite/client/plugins/microbot/example \
         runelite-client/src/main/java/net/runelite/client/plugins/microbot/mybot
   ```

2. **Rename and modify** the files:
   - `ExamplePlugin.java` → `MyBotPlugin.java`
   - `ExampleScript.java` → `MyBotScript.java`
   - `ExampleConfig.java` → `MyBotConfig.java`
   - `ExampleOverlay.java` → `MyBotOverlay.java`

3. **Update package declarations** and class names in all files

4. **Implement your bot logic** in the script file

5. **Rebuild the project**:
   ```bash
   mvn clean package
   ```

## Troubleshooting

### Windows-Specific Issues:

1. **Java version too old (Java 11 detected, need Java 17+)**:
   - Download Java 17 from [Adoptium](https://adoptium.net/)
   - Install it and make sure it's added to your PATH
   - Verify with: `java -version`

2. **Maven not found in PATH**:
   - If you installed Maven extension in VS Code but `mvn` command doesn't work in terminal:
     - **Option A**: Install Maven system-wide using Chocolatey:
       ```powershell
       # Install Chocolatey first
       Set-ExecutionPolicy Bypass -Scope Process -Force
       [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
       iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))
       
       # Then install Maven
       choco install maven
       ```
     - **Option B**: Use VS Code's integrated terminal with Maven extension
     - **Option C**: Download Maven manually and add to PATH

3. **PowerShell command syntax**: Use `;` instead of `&&` for command chaining:
   ```powershell
   # Correct
   cd "c:\Users\kyobh\projects\microbot"; mvn clean package
   
   # Incorrect
   cd "c:\Users\kyobh\projects\microbot" && mvn clean package
   ```

### Common Issues (All Platforms):

1. **"var" keyword errors**: Make sure you're using Java 17 or higher
2. **Maven not found**: Install Maven using package manager or manually
3. **Java version mismatch**: Set JAVA_HOME if needed:
   ```bash
   export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
   ```
4. **Gradle vs Maven**: This project works with both, but Maven is more reliable for this setup
5. **Maven plugin descriptor error**: If you get "No plugin descriptor found at META-INF/maven/plugin.xml":
   ```bash
   # Try building with install instead of compile
   mvn clean install -DskipTests
   
   # Or generate the plugin descriptor first
   cd runelite-maven-plugin
   mvn plugin:descriptor
   cd ..
   mvn clean compile
   ```
6. **Build failures in multi-module project**: Build modules in correct order:
   ```bash
   # Build the maven plugin first
   mvn clean install -pl runelite-maven-plugin -am
   
   # Then build the entire project
   mvn clean install -DskipTests
   
   # Finally package the client
   mvn clean package -pl runelite-client -am
   ```

### Alternative Build Strategy:
If Maven continues to fail, try this step-by-step approach:
```bash
# 1. Clean everything
mvn clean

# 2. Build and install dependencies first
mvn install -pl cache,runelite-api,runelite-maven-plugin -am -DskipTests

# 3. Generate plugin descriptors
cd runelite-maven-plugin
mvn plugin:descriptor
cd ..

# 4. Build the client
mvn package -pl runelite-client -am -DskipTests
```

### Verify Your Environment:
```bash
# Check Java version (should be 17+)
java -version

# Check Maven version
mvn -version

# Check if JAVA_HOME is set correctly
echo $JAVA_HOME
```

## IDE Setup (Optional)

### IntelliJ IDEA:
1. Open the project folder in IntelliJ
2. Import as Maven project
3. Set Project SDK to Java 17
4. Enable annotation processing for Lombok

### VS Code:
1. Install Java Extension Pack
2. Open the project folder
3. VS Code should automatically detect the Maven project

## Development Tips

1. **Use Maven for building** - It handles dependencies and annotation processing better than Gradle for this project
2. **Always rebuild after code changes** - The JAR needs to be regenerated
3. **Test on desktop environment** - The GUI requires a display
4. **Check logs** - Use `--debug` flag to see detailed output
5. **Use safe mode** - Start with `--safe-mode` to avoid plugin conflicts

## Next Steps

- Study the existing example scripts to understand the Microbot API
- Read the documentation in the `docs/` folder
- Join the Discord community for support
- Start with simple automation tasks before complex bots

Happy botting!
