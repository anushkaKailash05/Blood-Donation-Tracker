CREATE DATABASE blood_donation;
USE blood_donation;

-- HOSPITAL
CREATE TABLE Hospital (
    hospital_id INT PRIMARY KEY,
    email VARCHAR(100),
    name VARCHAR(100),
    contact_no VARCHAR(15),
    street VARCHAR(100),
    pincode VARCHAR(10),
    city VARCHAR(50)
);

-- DONOR
CREATE TABLE Donor (
    donor_id INT PRIMARY KEY,
    donor_name VARCHAR(100),
    gender CHAR(1),
    blood_group VARCHAR(5),
    contact_no VARCHAR(15),
    last_dn DATE,
    age INT,
    email VARCHAR(100),
    street VARCHAR(100),
    city VARCHAR(50),
    pincode VARCHAR(10),
    house_no INT
);

-- DONATION CAMP
CREATE TABLE DonationCamp (
    camp_id INT PRIMARY KEY,
    hospital_id INT,
    name VARCHAR(100),
    location VARCHAR(100),
    date DATE,
    contact_no VARCHAR(15),
    FOREIGN KEY (hospital_id)
        REFERENCES Hospital(hospital_id)
);

-- DONATION
CREATE TABLE Donation (
    donation_id INT PRIMARY KEY,
    donor_id INT,
    camp_id INT,
    donation_date DATE,
    quantity INT,
    FOREIGN KEY (donor_id)
        REFERENCES Donor(donor_id),
    FOREIGN KEY (camp_id)
        REFERENCES DonationCamp(camp_id)
);

-- BLOODBANK
CREATE TABLE BloodBank (
    bloodbank_id INT PRIMARY KEY,
    bag_id INT,
    name VARCHAR(100),
    location VARCHAR(100),
    contact_no VARCHAR(15),
    email VARCHAR(100),
    capacity INT
);

-- BLOODBAG
CREATE TABLE BloodBag (
    bag_id INT PRIMARY KEY,
    bloodbank_id INT,
    donation_id INT,
    blood_group VARCHAR(5),
    collected_date DATE,
    expiry_date DATE,
    quantity INT,
    FOREIGN KEY (bloodbank_id)
        REFERENCES BloodBank(bloodbank_id),
    FOREIGN KEY (donation_id)
        REFERENCES Donation(donation_id)
);

-- RECIPIENT
CREATE TABLE Recipient (
    recipient_id INT PRIMARY KEY,
    hospital_id INT,
    name VARCHAR(100),
    contact_no VARCHAR(15),
    email VARCHAR(100),
    gender CHAR(1),
    age INT,
    blood_group VARCHAR(5),
    FOREIGN KEY (hospital_id)
        REFERENCES Hospital(hospital_id)
);

-- REQUEST
CREATE TABLE Request (
    request_id INT PRIMARY KEY,
    recipient_id INT,
    bloodbank_id INT,
    request_date DATE,
    status VARCHAR(20),
    quantity INT,
    FOREIGN KEY (recipient_id)
        REFERENCES Recipient(recipient_id),
    FOREIGN KEY (bloodbank_id)
        REFERENCES BloodBank(bloodbank_id)
);
