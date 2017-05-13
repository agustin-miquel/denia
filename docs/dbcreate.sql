DROP DATABASE IF EXISTS denia;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE DATABASE denia CHARACTER SET utf8 COLLATE utf8_general_ci;

USE denia;

CREATE TABLE User (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  PRIMARY KEY (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Head (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  indexed BIT(1) NOT NULL,
  facebook BIT(1) NOT NULL,
  twitter BIT(1) NOT NULL,
  twitterTag VARCHAR(255),
  page_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_head_page
    FOREIGN KEY (page_id)
    REFERENCES Page (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Address (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255),
  street VARCHAR(255),
  town VARCHAR(255),
  postalCode VARCHAR(255),
  phone VARCHAR(255),
  page_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_address_page
    FOREIGN KEY (page_id)
    REFERENCES Page (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Section (
  id INT NOT NULL AUTO_INCREMENT,
  title VARCHAR(255),
  content TEXT NOT NULL,
  showSection BIT(1) NOT NULL,
  showInMenu BIT(1) NOT NULL,
  page_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_section_page
    FOREIGN KEY (page_id)
    REFERENCES Page (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Contact (
  id INT NOT NULL AUTO_INCREMENT,
  contactDate DATETIME NOT NULL,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(100),
  phone VARCHAR(25),
  description TEXT,
  userName VARCHAR(100) NOT NULL,
  sentTo VARCHAR(255) NOT NULL,
  result TEXT NOT NULL,
  page_id INT NOT NULL,
  PRIMARY KEY (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Body (
  id INT NOT NULL AUTO_INCREMENT,
  h1 VARCHAR(255) NOT NULL,
  h2 TEXT,
  showSections BIT(1) NOT NULL,
  showContact BIT(1) NOT NULL,
  showMap BIT(1) NOT NULL,
  page_id INT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FK_body_page
    FOREIGN KEY (page_id)
    REFERENCES Page (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Page (
  id INT NOT NULL AUTO_INCREMENT,
  pageName VARCHAR(25) NOT NULL,
  description VARCHAR(250),
  email VARCHAR(250),
  body_id INT,
  head_id INT,
  address_id INT,
  PRIMARY KEY (id),
  CONSTRAINT FK_page_body
    FOREIGN KEY (body_id)
    REFERENCES Body (id),
  CONSTRAINT FK_page_head
    FOREIGN KEY (head_id)
    REFERENCES Head (id),
  CONSTRAINT FK_page_address
    FOREIGN KEY (address_id)
    REFERENCES Address (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Page_Section (
  Page_id INT NOT NULL,
  sections_id INT NOT NULL,
  UNIQUE INDEX UK_section_id (sections_id ASC),
  CONSTRAINT FK_page_section
    FOREIGN KEY (Page_id)
    REFERENCES Page (id),
  CONSTRAINT FK_section
    FOREIGN KEY (sections_id)
    REFERENCES Section (id))
DEFAULT CHARACTER SET = utf8;

CREATE TABLE Page_Contact (
  Page_id INT NOT NULL,
  contacts_id INT NOT NULL,
  UNIQUE INDEX UK_contact_id (contacts_id ASC),
  CONSTRAINT FK_page_contact
    FOREIGN KEY (Page_id)
    REFERENCES Page (id),
  CONSTRAINT FK_contact
    FOREIGN KEY (contacts_id)
    REFERENCES Contact (id))
DEFAULT CHARACTER SET = utf8;
