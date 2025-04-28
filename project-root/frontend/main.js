frontend / main.js
const apiUrl = '/api/products';
async function fetchProducts() {
    const res = await fetch(apiUrl);
    const data = await res.json();
    renderList(data);
}
function renderList(products) {
    const list = document.getElementById('product-list');
    list.innerHTML = products.map(p => `
<div data-id="${p.id}">
    <strong>${p.name}</strong> — $${p.price} — Stock: ${p.stock}
    <button class="edit">Editar</button>
    <button class="delete">Eliminar</button>
</div>
`).join('');
    attachListeners();
}
function attachListeners() {
    document.querySelectorAll('.edit').forEach(btn => {
        btn.onclick = () => loadForm(btn.closest('div').dataset.id);
    });
    document.querySelectorAll('.delete').forEach(btn => {
        btn.onclick = () =>
            deleteProduct(btn.closest('div').dataset.id);
    });
}
async function loadForm(id) {
    const res = await fetch(`${apiUrl}/${id}`);
    const p = await res.json();
    document.getElementById('product-id').value = p.id;
    document.getElementById('name').value = p.name;
    document.getElementById('description').value = p.description;
    document.getElementById('price').value = p.price;
    document.getElementById('stock').value = p.stock;
}
async function deleteProduct(id) {
    await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
    fetchProducts();
}
document.getElementById('product-form').onsubmit = async e => {
    e.preventDefault();
    const id = document.getElementById('product-id').value;
    const payload = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        stock: parseInt(document.getElementById('stock').value, 10),
    };
    const opts = {
        method: id ? 'PUT' : 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
    };
    const url = id ? `${apiUrl}/${id}` : apiUrl;
    await fetch(url, opts);
    e.target.reset();
    fetchProducts();
};
// Al iniciar
fetchProducts();