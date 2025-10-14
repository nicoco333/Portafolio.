import React, { useEffect, useState } from "react";
import { postService } from "./services/postService";

const USER_ID = 1;

export default function App() {
  const [posts, setPosts] = useState([]);
  const [form, setForm] = useState({ title: "", body: "" });
  const [editId, setEditId] = useState(null);

  useEffect(() => {
    postService.getAllByUser(USER_ID)
      .then(res => setPosts(res.data))
      .catch(err => console.error("Error al obtener posts:", err));
  }, []);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const payload = { ...form, userId: USER_ID };

    const action = editId
      ? postService.update(editId, payload)
      : postService.create(payload);

    action
      .then((res) => {
        if (editId) {
          setPosts(posts.map(p => p.id === editId ? res.data : p));
        } else {
          setPosts([...posts, { ...res.data, id: posts.length + 101 }]);
        }
        setForm({ title: "", body: "" });
        setEditId(null);
      })
      .catch(err => console.error("Error al guardar:", err));
  };

  const handleDelete = (id) => {
    postService.remove(id)
      .then(() => setPosts(posts.filter(p => p.id !== id)))
      .catch(err => console.error("Error al eliminar:", err));
  };

  const handleEdit = (post) => {
    setForm({ title: post.title, body: post.body });
    setEditId(post.id);
  };

  return (
    <div className="container py-4">
      <h1 className="mb-4">Mis Posts (axios + servicios + Bootstrap)</h1>

      <form className="mb-4" onSubmit={handleSubmit}>
        <div className="mb-3">
          <input
            name="title"
            placeholder="Título"
            className="form-control"
            value={form.title}
            onChange={handleChange}
          />
        </div>
        <div className="mb-3">
          <textarea
            name="body"
            placeholder="Contenido"
            className="form-control"
            value={form.body}
            onChange={handleChange}
          />
        </div>
        <button className="btn btn-success" type="submit">
          {editId ? "Actualizar" : "Crear"}
        </button>
      </form>

      <div className="row row-cols-1 row-cols-md-2 g-4">
        {posts.map((post) => (
          <div className="col" key={post.id}>
            <div className="card h-100 shadow-sm">
              <div className="card-body">
                <h5 className="card-title">{post.title}</h5>
                <p className="card-text">{post.body}</p>
              </div>
              <div className="card-footer d-flex justify-content-end gap-2">
                <button
                  className="btn btn-outline-primary btn-sm"
                  onClick={() => handleEdit(post)}
                >
                  <i className="bi bi-pencil"></i> Editar
                </button>
                <button
                  className="btn btn-outline-danger btn-sm"
                  onClick={() => handleDelete(post.id)}
                >
                  <i className="bi bi-trash3"></i> Eliminar
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
