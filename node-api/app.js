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

// Define a route to get fabric information
app.get('/fabrics', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM Fabric_information');
        res.json(rows);
    } catch (error) {
        console.error('Error fetching fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

// Define a route to get defect information
app.get('/defects', async (req, res) => {
    try {
        const [rows] = await pool.query('SELECT * FROM Defect_information');
        res.json(rows);
    } catch (error) {
        console.error('Error fetching defect information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// Define a route to get fabric information by ID
app.get('/fabrics/:fabric_code', async (req, res) => {
    const fabricId = req.params.fabric_code;
    try {
        const [rows] = await pool.query('SELECT * FROM Fabric_information WHERE fabric_code = ?', [fabricId]);
        if (rows.length === 0) {
            res.status(404).json({ error: 'Fabric not found' });
        } else {
            res.json(rows[0]);
        }
    } catch (error) {
        console.error('Error fetching fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

// Define a route to delete fabric information by ID
app.delete('/fabrics-delete/:fabric_code', async (req, res) => {
    const fabricId = req.params.fabric_code;
    try {
        await pool.query('DELETE FROM Fabric_information WHERE fabric_code = ?', [fabricId]);
        res.json({ message: 'Fabric deleted successfully' });
    } catch (error) {
        console.error('Error deleting fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// Define a route to get defect information by ID
app.get('/defect/:defect_code', async (req, res) => {
    const fabricId = req.params.defect_code;
    try {
        const [rows] = await pool.query('SELECT * FROM Defect_information WHERE defect_code = ?', [fabricId]);
        if (rows.length === 0) {
            res.status(404).json({ error: 'Defect not found' });
        } else {
            res.json(rows[0]);
        }
    } catch (error) {
        console.error('Error fetching defect information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// Define a route to delete defect information by ID
app.delete('/defect-delete/:defect_code', async (req, res) => {
    const defectfId = req.params.defect_code;
    try {
        await pool.query('DELETE FROM Defect_information WHERE defect_code = ?', [fabricId]);
        res.json({ message: 'Fabric deleted successfully' });
    } catch (error) {
        console.error('Error deleting defect information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
