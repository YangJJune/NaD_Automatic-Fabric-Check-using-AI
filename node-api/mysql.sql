
USE NAD_test;

-- Create Fabric_information table
CREATE TABLE Fabric_information (
    fabric_code VARCHAR(20) PRIMARY KEY,
    total_tests INT,
    defects_detected INT,
    scan_start_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    test_end_time TIMESTAMP,
    image_path VARCHAR(100),
    status ENUM('running', 'complete') DEFAULT 'running'
);

-- Create Defect_information table
CREATE TABLE Defect_information (
    defect_code VARCHAR(20) PRIMARY KEY,
    detection_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    x_coordinate FLOAT,
    y_coordinate FLOAT,
    defect_type VARCHAR(50),
    image_path VARCHAR(100)
);


-- Insert data into Fabric_information table
INSERT INTO Fabric_information (fabric_code, total_tests, defects_detected, test_end_time, image_path, status)
VALUES
    ('F001', 100, 5, '2023-10-27 12:00:00', '/images/fabric1.jpg', 'complete'),
    ('F002', 120, 8, '2023-10-27 13:30:00', '/images/fabric2.jpg', 'complete'),
    ('F003', 150, 12, '2023-10-27 15:00:00', '/images/fabric3.jpg', 'complete'),
    ('F004', 80, 3, NULL, '/images/fabric4.jpg', 'running'),
    ('F005', 200, 15, NULL, '/images/fabric5.jpg', 'running'),
    ('F006', 90, 7, '2023-10-27 14:00:00', '/images/fabric6.jpg', 'complete'),
    ('F007', 110, 10, NULL, '/images/fabric7.jpg', 'running'),
    ('F008', 130, 9, '2023-10-27 16:00:00', '/images/fabric8.jpg', 'complete'),
    ('F009', 95, 6, '2023-10-27 14:30:00', '/images/fabric9.jpg', 'complete'),
    ('F010', 85, 4, NULL, '/images/fabric10.jpg', 'running');

-- Insert data into Defect_information table
INSERT INTO Defect_information (defect_code, detection_time, x_coordinate, y_coordinate, defect_type, image_path)
VALUES
    ('D001', '2023-10-27 12:10:00', 10.5, 20.3, 'Color Fade', '/images/defect1.jpg'),
    ('D002', '2023-10-27 13:45:00', 5.8, 15.2, 'Stitching Issue', '/images/defect2.jpg'),
    ('D003', '2023-10-27 15:20:00', 8.3, 12.6, 'Tear', '/images/defect3.jpg'),
    ('D004', '2023-10-27 14:15:00', 15.7, 25.0, 'Uneven Dye', '/images/defect4.jpg'),
    ('D005', '2023-10-27 16:30:00', 12.2, 18.5, 'Hole', '/images/defect5.jpg'),
    ('D006', '2023-10-27 14:45:00', 7.9, 14.0, 'Color Bleeding', '/images/defect6.jpg'),
    ('D007', '2023-10-27 16:10:00', 9.1, 22.8, 'Shrinkage', '/images/defect7.jpg'),
    ('D008', '2023-10-27 13:00:00', 6.3, 16.7, 'Snag', '/images/defect8.jpg'),
    ('D009', '2023-10-27 15:50:00', 11.2, 19.4, 'Pilling', '/images/defect9.jpg'),
    ('D010', '2023-10-27 17:00:00', 14.0, 24.3, 'Misprint', '/images/defect10.jpg');