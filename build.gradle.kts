

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
	// https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
	implementation("org.springdoc:springdoc-openapi-ui:1.7.0")

	testImplementation("junit:junit:4.13.1")
	testImplementation("org.projectlombok:lombok:1.18.28")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
