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

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
