
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


ALTER TABLE Fabric_information
ADD COLUMN total_test_time INT;

ALTER TABLE Fabric_information
ADD COLUMN performance FLOAT;

UPDATE Fabric_information
SET performance = (defects_detected / total_tests) * 100;



INSERT INTO Fabric_information (fabric_code, total_tests, defects_detected, test_end_time, image_path, status, total_test_time, performance)
VALUES
  ('Fabric001', 100, 5, '2023-11-14 08:30:00', '/path/to/image1.jpg', 'complete', 3600, 5),
  ('Fabric002', 150, 10, '2023-11-14 09:45:00', '/path/to/image2.jpg', 'complete', 4500, 6.67),
  ('Fabric003', 200, 20, '2023-11-14 10:30:00', '/path/to/image3.jpg', 'complete', 5400, 10),
  ('Fabric004', 120, 8, '2023-11-14 11:15:00', '/path/to/image4.jpg', 'complete', 4320, 6.67),
  ('Fabric005', 180, 15, '2023-11-14 12:00:00', '/path/to/image5.jpg', 'complete', 6480, 8.33),
  ('Fabric006', 90, 7, '2023-11-14 13:30:00', '/path/to/image6.jpg', 'complete', 3240, 7.78),
  ('Fabric007', 110, 9, '2023-11-14 14:15:00', '/path/to/image7.jpg', 'complete', 3960, 8.18),
  ('Fabric008', 160, 12, '2023-11-14 15:00:00', '/path/to/image8.jpg', 'complete', 5760, 7.5),
  ('Fabric009', 130, 11, '2023-11-14 16:00:00', '/path/to/image9.jpg', 'complete', 4680, 8.46),
  ('Fabric010', 140, 14, '2023-11-14 17:00:00', '/path/to/image10.jpg', 'complete', 5040, 10),
  ('Fabric011', 100, 6, '2023-11-14 18:00:00', '/path/to/image11.jpg', 'complete', 3600, 6),
  ('Fabric012', 200, 25, '2023-11-14 19:00:00', '/path/to/image12.jpg', 'complete', 7200, 12.5),
  ('Fabric013', 150, 18, '2023-11-14 20:00:00', '/path/to/image13.jpg', 'complete', 5400, 12),
  ('Fabric014', 180, 22, '2023-11-14 21:00:00', '/path/to/image14.jpg', 'complete', 6480, 12.22),
  ('Fabric015', 110, 8, '2023-11-14 22:00:00', '/path/to/image15.jpg', 'complete', 3960, 7.27);


  INSERT INTO Fabric_information (fabric_code, total_tests, defects_detected, test_end_time, image_path, status, total_test_time, performance)
VALUES
  ('Fabric016', 80, 0, NULL, '/path/to/image16.jpg', 'running', NULL, NULL),
  ('Fabric017', 95, 0, NULL, '/path/to/image17.jpg', 'running', NULL, NULL),
  ('Fabric018', 110, 0, NULL, '/path/to/image18.jpg', 'running', NULL, NULL),
  ('Fabric019', 75, 0, NULL, '/path/to/image19.jpg', 'running', NULL, NULL),
  ('Fabric020', 105, 0, NULL, '/path/to/image20.jpg', 'running', NULL, NULL),
  ('Fabric021', 70, 0, NULL, '/path/to/image21.jpg', 'running', NULL, NULL),
  ('Fabric022', 90, 0, NULL, '/path/to/image22.jpg', 'running', NULL, NULL),
  ('Fabric023', 100, 0, NULL, '/path/to/image23.jpg', 'running', NULL, NULL),
  ('Fabric024', 85, 0, NULL, '/path/to/image24.jpg', 'running', NULL, NULL),
  ('Fabric025', 120, 0, NULL, '/path/to/image25.jpg', 'running', NULL, NULL),
  ('Fabric026', 78, 0, NULL, '/path/to/image26.jpg', 'running', NULL, NULL),
  ('Fabric027', 92, 0, NULL, '/path/to/image27.jpg', 'running', NULL, NULL),
  ('Fabric028', 88, 0, NULL, '/path/to/image28.jpg', 'running', NULL, NULL),
  ('Fabric029', 102, 0, NULL, '/path/to/image29.jpg', 'running', NULL, NULL),
  ('Fabric030', 115, 0, NULL, '/path/to/image30.jpg', 'running', NULL, NULL);



INSERT INTO Defect_information (defect_code, detection_time, x_coordinate, y_coordinate, defect_type, image_path)
VALUES
  ('Defect001', '2023-11-14 08:45:00', 10, 15, 'Tear', '/path/to/defect_image1.jpg'),
  ('Defect002', '2023-11-14 09:55:00', 20, 30, 'Color Fade', '/path/to/defect_image2.jpg'),
  ('Defect003', '2023-11-14 10:45:00', 15, 25, 'Stitching Issue', '/path/to/defect_image3.jpg'),
  ('Defect004', '2023-11-14 11:30:00', 5, 10, 'Hole', '/path/to/defect_image4.jpg'),
  ('Defect005', '2023-11-14 12:15:00', 30, 40, 'Uneven Dye', '/path/to/defect_image5.jpg'),
  ('Defect006', '2023-11-14 13:45:00', 12, 18, 'Tear', '/path/to/defect_image6.jpg'),
  ('Defect007', '2023-11-14 14:30:00', 22, 32, 'Color Fade', '/path/to/defect_image7.jpg'),
  ('Defect008', '2023-11-14 15:15:00', 17, 27, 'Stitching Issue', '/path/to/defect_image8.jpg'),
  ('Defect009', '2023-11-14 16:15:00', 8, 12, 'Hole', '/path/to/defect_image9.jpg'),
  ('Defect010', '2023-11-14 17:15:00', 25, 35, 'Uneven Dye', '/path/to/defect_image10.jpg'),
  ('Defect011', '2023-11-14 18:30:00', 14, 22, 'Tear', '/path/to/defect_image11.jpg'),
  ('Defect012', '2023-11-14 19:45:00', 28, 38, 'Color Fade', '/path/to/defect_image12.jpg'),
  ('Defect013', '2023-11-14 20:30:00', 19, 29, 'Stitching Issue', '/path/to/defect_image13.jpg'),
  ('Defect014', '2023-11-14 21:15:00', 7, 11, 'Hole', '/path/to/defect_image14.jpg'),
  ('Defect015', '2023-11-14 22:30:00', 33, 45, 'Uneven Dye', '/path/to/defect_image15.jpg');
