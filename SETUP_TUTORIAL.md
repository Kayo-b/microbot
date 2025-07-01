# Microbot Java Project Setup Tutorial

This tutorial will guide you through setting up and building the Microbot Java project using Maven on Ubuntu/Linux.

## Prerequisites

1. **Java 17 or higher** - Required for the project to compile properly
2. **Maven** - Build tool for managing dependencies and compilation
3. **Git** - For cloning the repository (if needed)

## Step 1: Install Required Software

### Install Java 17 (OpenJDK)
```bash
# Update package list
sudo apt update

# Install OpenJDK 17
sudo apt install openjdk-17-jdk

# Verify installation
java -version
javac -version
```

### Install Maven
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

### Clean and compile the entire project:
```bash
mvn clean compile
```

### Build the JAR file with all dependencies:
```bash
mvn clean package
```

This will create the executable JAR file in:
`runelite-client/target/microbot-1.9.2.jar`

## Step 4: Development Workflow

### Making Changes to Scripts

1. **Edit your script files** (e.g., `ExampleScript.java`, `ExamplePlugin.java`)
2. **Rebuild the project** after making changes:
   ```bash
   mvn clean package
   ```
3. **Run the updated JAR**:
   ```bash
   cd runelite-client/target
   java -jar microbot-1.9.2.jar
   ```

### Quick Build (without tests)
If you want faster builds during development:
```bash
mvn clean package -DskipTests
```

### Build only the client module
```bash
mvn clean package -pl runelite-client -am
```

## Step 5: Running the Application

### On Desktop Ubuntu (with GUI):
```bash
cd runelite-client/target
java -jar microbot-1.9.2.jar
```

### Common run options:
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

### Common Issues:

1. **"var" keyword errors**: Make sure you're using Java 17 or higher
2. **Maven not found**: Install Maven using `sudo apt install maven`
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
