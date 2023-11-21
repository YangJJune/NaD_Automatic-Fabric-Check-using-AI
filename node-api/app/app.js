import express from 'express';
import mysql from 'mysql2';
import dotenv from 'dotenv';

dotenv.config();

const app = express();

const pool = mysql.createPool({
    host: process.env.MYSQL_HOST,
    user: process.env.MYSQL_USER,
    password: process.env.MYSQL_PASSWORD,
    database: process.env.MYSQL_DATABASE,
}).promise();


// Define a route to get fabric information by fabric_id
app.get('/fabric/:fabric_id', async (req, res) => {
    const fabric_id = req.params.fabric_id;
    try {
        const [rows] = await pool.query('SELECT * FROM fabric_information WHERE fabric_id = ?', [fabric_id]);
        if (rows.length === 0) {
            res.status(404).json({ error: 'Fabric not found' });
        } else {
            const fabric_information = rows[0];
            res.json({
                fabric_id: fabric_information.fabric_id,
                total_count: fabric_information.total_count,
                defect_count: fabric_information.defect_count,
                scan_start_time: fabric_information.scan_start_time,
                complete_time: fabric_information.complete_time,
                image_path: fabric_information.image_path,
            });
        }
    } catch (error) {
        console.error('Error fetching fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


app.get('/fabric/:fabric_id', async (req, res) => {
    const fabric_id = req.params.fabric_id;
    try {
        const [rows] = await pool.query('SELECT * FROM fabric_information');
        if (rows.length === 0) {
            res.status(404).json({ error: 'Fabric not found' });
        } else {
            const fabric_information = rows[0];
            res.json({
                fabric_id: fabric_information.fabric_id,
                total_count: fabric_information.total_count,
                defect_count: fabric_information.defect_count,
                scan_start_time: fabric_information.scan_start_time,
                complete_time: fabric_information.complete_time,
                image_path: fabric_information.image_path,
            });
        }
    } catch (error) {
        console.error('Error fetching fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// all fabric information
app.get('/fabrics', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM fabric_information');
        res.json(rows);
    } catch (error) {
        console.error('Error fetching fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// create fabric information
app.post('/add-fabric/:fabric_id', async (req, res) => {
    const fabricId = req.params.fabric_id;
    const { fabric_id, image_path } = req.body;

    try {
        const sql = 'INSERT INTO fabric_information (fabric_id, image_path) VALUES (?, ?)';
        await pool.query(sql, [fabric_id, image_path]);

        console.log('Fabric information added successfully');
        res.json({ message: 'Fabric information added successfully' });
    } catch (error) {
        console.error('Error adding fabric information:', error);
    }
});


// set fabric scan as complete
app.get('/complete-fabric/:fabric_id', async (req, res) => {
    const fabricId = req.params.fabric_id;

    try {
        // Get the current timestamp
        const completeTime = new Date().toISOString();

        // Update the complete_time for the specified fabric_id
        const sql = 'UPDATE fabric_information SET complete_time = ? WHERE fabric_id = ?';
        await pool.query(sql, [completeTime, fabricId]);

        console.log(`Fabric scan for fabric_id ${fabricId} marked as complete.`);
        res.json({ message: `Fabric scan for fabric_id ${fabricId} marked as complete.` });
    } catch (error) {
        console.error('Error marking fabric scan as complete:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// mark fabric as no-defect
app.get('/ok-fabric/:fabric_id', async (req, res) => {
    const fabricId = req.params.fabric_id;

    try {
        // Update the total_count for the specified fabric_id by incrementing it by 1
        const sql = 'UPDATE fabric_information SET total_count = total_count + 1 WHERE fabric_id = ?';
        await pool.query(sql, [fabricId]);

        console.log(`Fabric with fabric_id ${fabricId} marked as no-defect.`);
        res.json({ message: `Fabric with fabric_id ${fabricId} marked as no-defect.` });
    } catch (error) {
        console.error('Error marking fabric as no-defect:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});



// create a defect
app.post('/add-defect', async (req, res) => {
    const { parent_fabric, issue_name, x, y, image_path } = req.body;
    const timestamp = new Date().toISOString().replace(/[^0-9]/g, ''); // format timestamp

    try {
        const defectCode = timestamp; 
        const sql = 'INSERT INTO defect_information (defect_code, parent_fabric, timestamp, issue_name, x, y, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)';
        await pool.query(sql, [defectCode, parent_fabric, timestamp, issue_name, x, y, image_path]);

        // update the parent fabric's defect_count and total_count
        const updateSql = 'UPDATE fabric_information SET defect_count = defect_count + 1, total_count = total_count + 1 WHERE fabric_id = ?';
        await pool.query(updateSql, [parent_fabric]);

        console.log(`Defect added with defect_code ${defectCode}.`);
        res.json({ message: `Defect added with defect_code ${defectCode}.` });
    } catch (error) {
        console.error('Error adding defect:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

// defects by parent fabric
app.get('/defects/:parent_fabric', async (req, res) => {
    const fabricId = req.params.parent_fabric;

    try {
        
        const sql = 'SELECT * FROM defect_information WHERE parent_fabric = ?';
        const [rows] = await pool.query(sql, [fabricId]);

        res.json(rows);
    } catch (error) {
        console.error('Error fetching defects:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// all defect information
app.get('/defects', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM defect_information');
        res.json(rows);
    } catch (error) {
        console.error('Error fetching defect information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});





const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
