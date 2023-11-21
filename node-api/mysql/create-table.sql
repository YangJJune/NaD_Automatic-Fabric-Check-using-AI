USE NAD_test;
CREATE TABLE NAD_test.fabric_information (
    fabric_id INT PRIMARY KEY,
    scan_start_time TIMESTAMP,
    complete_time TIMESTAMP NULL,
    defect_count INT,
    total_count INT,
    image_path VARCHAR(255)
);


CREATE TABLE NAD_test.defect_information (
    defect_code VARCHAR(14) PRIMARY KEY,
    parent_fabric INT,
    timestamp TIMESTAMP,
    issue_name ENUM('hole', 'stain', 'puckering'),
    x INT,
    y INT,
    image_path VARCHAR(255),
    FOREIGN KEY (parent_fabric) REFERENCES NAD_test.fabric_information(fabric_id)
);

INSERT INTO NAD_test.fabric_information (fabric_id, scan_start_time, complete_time, defect_count, total_count, image_path) VALUES
(1, '2023-11-21 08:00:00', '2023-11-21 08:30:00', 2, 5, '/images/fabric1.jpg'),
(2, '2023-11-21 09:00:00', '2023-11-21 09:25:00', 1, 6, '/images/fabric2.jpg'),
(3, '2023-11-21 10:00:00', NULL, 0, 7, '/images/fabric3.jpg'),
(4, '2023-11-21 11:00:00', '2023-11-21 11:35:00', 3, 8, '/images/fabric4.jpg'),
(5, '2023-11-21 12:00:00', NULL, 0, 9, '/images/fabric5.jpg'),
(6, '2023-11-21 13:00:00', '2023-11-21 13:20:00', 2, 10, '/images/fabric6.jpg'),
(7, '2023-11-21 14:00:00', NULL, 0, 11, '/images/fabric7.jpg'),
(8, '2023-11-21 15:00:00', '2023-11-21 15:45:00', 4, 12, '/images/fabric8.jpg'),
(9, '2023-11-21 16:00:00', NULL, 0, 13, '/images/fabric9.jpg'),
(10, '2023-11-21 17:00:00', '2023-11-21 17:30:00', 1, 14, '/images/fabric10.jpg');

ALTER TABLE NAD_test.defect_information
MODIFY COLUMN defect_code VARCHAR(50); -- Change 50 to the desired length


INSERT INTO NAD_test.defect_information (defect_code, parent_fabric, timestamp, issue_name, x, y, image_path)
VALUES
    ('20231121140000-1', 1, '2023-11-21 14:05:00', 'hole', 10.0, 20.0, '/path/to/defect_image1.jpg'),
    ('20231122101500-2', 2, '2023-11-22 10:20:00', 'stain', 15.0, 25.0, '/path/to/defect_image2.jpg'),
    ('20231123103000-3', 3, '2023-11-23 10:35:00', 'puckering', 20.0, 30.0, '/path/to/defect_image3.jpg'),
    ('20231124154500-4', 4, '2023-11-24 15:50:00', 'hole', 25.0, 35.0, '/path/to/defect_image4.jpg'),
    ('20231125120000-5', 5, '2023-11-25 12:05:00', 'stain', 30.0, 40.0, '/path/to/defect_image5.jpg'),
    ('20231126173000-6', 6, '2023-11-26 17:35:00', 'hole', 35.0, 45.0, '/path/to/defect_image6.jpg'),
    ('20231127142000-7', 7, '2023-11-27 14:25:00', 'puckering', 40.0, 50.0, '/path/to/defect_image7.jpg'),
    ('20231128111000-8', 8, '2023-11-28 11:15:00', 'hole', 45.0, 55.0, '/path/to/defect_image8.jpg'),
    ('20231129164500-9', 9, '2023-11-29 16:50:00', 'stain', 50.0, 60.0, '/path/to/defect_image9.jpg'),
    ('20231130132500-10', 10, '2023-11-30 13:30:00', 'hole', 55.0, 65.0, '/path/to/defect_image10.jpg');
