import React, { useState, useEffect } from 'react'

// The Java backend endpoint (StudentApiServlet). Change if your port differs.
const API = 'http://localhost:8080/api/students'

/**
 * A React component is just a function that returns UI (JSX).
 * React re-runs this function and re-draws the screen whenever "state" changes.
 *
 * Two core hooks:
 *   useState  -> remember a value between renders (the students list, form fields)
 *   useEffect -> run code after render, e.g. fetch data once when the page loads
 */
export default function App() {
  const [students, setStudents] = useState([])              // the list from the backend
  const [form, setForm] = useState({ name: '', email: '', course: '' })

  // Load students from the Java backend when the component first appears.
  // The empty array [] means "run this only once".
  useEffect(() => { loadStudents() }, [])

  function loadStudents() {
    fetch(API)                       // GET /api/students
      .then(res => res.json())       // parse the JSON the servlet returned
      .then(data => setStudents(data))
      .catch(err => console.error('Backend not reachable:', err))
  }

  // Update one field as the user types. e.target.name matches the input's name.
  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value })
  }

  function handleSubmit(e) {
    e.preventDefault()               // stop the browser's default page reload
    const body = new URLSearchParams(form)   // send as form fields the servlet reads
    fetch(API, { method: 'POST', body })
      .then(() => {
        setForm({ name: '', email: '', course: '' })   // clear the form
        loadStudents()                                  // refresh the list
      })
  }

  function handleDelete(id) {
    if (!confirm('Delete this student?')) return
    fetch(`${API}?id=${id}`, { method: 'DELETE' }).then(loadStudents)
  }

  return (
    <div style={{ fontFamily: 'Arial', margin: 40, color: '#222' }}>
      <h1>Students (React frontend)</h1>
      <p>React talks to the same Java + JDBC backend over JSON.</p>

      <form onSubmit={handleSubmit} style={{ marginBottom: 20 }}>
        <input name="name"   placeholder="Name"   value={form.name}   onChange={handleChange} required />
        <input name="email"  placeholder="Email"  value={form.email}  onChange={handleChange} required />
        <input name="course" placeholder="Course" value={form.course} onChange={handleChange} required />
        <button type="submit">Add</button>
      </form>

      <table border="1" cellPadding="8" style={{ borderCollapse: 'collapse' }}>
        <thead>
          <tr><th>ID</th><th>Name</th><th>Email</th><th>Course</th><th></th></tr>
        </thead>
        <tbody>
          {/* .map turns each student object into a table row.
              key={s.id} helps React track rows efficiently. */}
          {students.map(s => (
            <tr key={s.id}>
              <td>{s.id}</td>
              <td>{s.name}</td>
              <td>{s.email}</td>
              <td>{s.course}</td>
              <td><button onClick={() => handleDelete(s.id)}>Delete</button></td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}
