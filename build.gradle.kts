

plugins {
	java
	application
	id("org.springframework.boot") version "2.7.16-SNAPSHOT"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
}

group = "com.takehometask"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17

}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
	maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-websocket")
	implementation("org.hibernate.validator:hibernate-validator")
	testImplementation("junit:junit:4.13.1")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	// https://mvnrepository.com/artifact/org.testng/testng
	testImplementation("org.testng:testng:7.8.0")


}
tasks.withType<Test>().configureEach {
	useJUnitPlatform {
		excludeTags("optional")
	}
	useTestNG {
		val options = this as TestNGOptions
		options.includeGroups("optional")
	}

	jvmArgs = listOf(
		"--add-opens", "java.base/java.util.concurrent=ALL-UNNAMED",
		"--add-opens", "java.base/java.lang=ALL-UNNAMED",
		"--add-opens", "java.base/java.security=ALL-UNNAMED",
		"-Xmx2g"
	)
}
