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
app.get('/fabrics/:id', async (req, res) => {
    const fabricId = req.params.id;
    try {
        const [rows] = await pool.query('SELECT * FROM Fabric_information WHERE id = ?', [fabricId]);
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

// Define a route to create a new fabric entry
app.post('/fabrics', async (req, res) => {
    const fabricData = req.body;
    try {
        await pool.query('INSERT INTO Fabric_information SET ?', [fabricData]);
        res.status(201).json({ message: 'Fabric added successfully' });
    } catch (error) {
        console.error('Error creating fabric entry:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

// Define a route to update fabric information by ID
app.put('/fabrics/:id', async (req, res) => {
    const fabricId = req.params.id;
    const updatedData = req.body;
    try {
        await pool.query('UPDATE Fabric_information SET ? WHERE id = ?', [updatedData, fabricId]);
        res.json({ message: 'Fabric updated successfully' });
    } catch (error) {
        console.error('Error updating fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

// Define a route to delete fabric information by ID
app.delete('/fabrics/:id', async (req, res) => {
    const fabricId = req.params.id;
    try {
        await pool.query('DELETE FROM Fabric_information WHERE id = ?', [fabricId]);
        res.json({ message: 'Fabric deleted successfully' });
    } catch (error) {
        console.error('Error deleting fabric information:', error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});


// Define a route to get defect information by ID
app.get('/defect/:id', async (req, res) => {
    const fabricId = req.params.id;
    try {
        const [rows] = await pool.query('SELECT * FROM Defect_information WHERE id = ?', [fabricId]);
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
app.delete('/defect/:id', async (req, res) => {
    const fabricId = req.params.id;
    try {
        await pool.query('DELETE FROM Defect_information WHERE id = ?', [fabricId]);
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
