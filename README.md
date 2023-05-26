Create an empty maven project in IntellijJ and maken it multimodule by adding <packaging>pom</packaging>
Also remove the src folder

Add a module without sample code in the parent in intellij and name it cdk
Add a package org.example.cdklambda.cdk
Add the basic App and Stack class
Add this to the pom of the cdk module

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>

        <cdk.version>2.44.0</cdk.version>
        <junit.version>5.9.1</junit.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>software.amazon.awscdk</groupId>
            <artifactId>aws-cdk-lib</artifactId>
            <version>${cdk.version}</version>
        </dependency>
        <dependency>
            <groupId>software.constructs</groupId>
            <artifactId>constructs</artifactId>
            <version>10.1.120</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.23.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.10.1</version>
                <configuration>
                    <source>11</source>
                    <target>11</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.1.0</version>
                <configuration>
                    <mainClass>org.example.cdklambda.cdk.CdkLambdaApp</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>


Build the main project
From cdk folder cdf diff or cdk deploy

Create a new module for the lambda
Create a package
Add plugin and dependencies in the pom
Create a simple Lambda class
Build the whole project
