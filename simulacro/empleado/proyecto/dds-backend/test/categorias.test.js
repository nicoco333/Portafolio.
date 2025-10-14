const request = require("supertest");
const app = require("../index");

describe("GET /api/categorias", function () {
    it("Devolveria todos los categorias", async function () {
        const res = await request(app)
            .get("/api/categorias")
            .set("content-type", "application/json");
        expect(res.headers["content-type"]).toEqual(
            "application/json; charset=utf-8"
        );
        expect(res.statusCode).toEqual(200);
        expect(res.body).toEqual(
            expect.arrayContaining([
                expect.objectContaining({
                    IdCategoria: expect.any(Number),
                    Nombre: expect.any(String),
                }),
            ])
        );
    });
});

let categoriaId; // Variable para almacenar el IdCategoria de la categoría creada

describe("POST /api/categorias", function () {
    it("Debería crear una nueva categoría", async function () {
        const nuevaCategoria = {
            Nombre: "NUEVA CATEGORIA"
        };
        const res = await request(app)
            .post("/api/categorias")
            .send(nuevaCategoria)
            .set("content-type", "application/json");
        expect(res.statusCode).toEqual(201);
        expect(res.body).toEqual(
            expect.objectContaining({
                IdCategoria: expect.any(Number),
                Nombre: "NUEVA CATEGORIA",
            })
        );
        categoriaId = res.body.IdCategoria; // Guardar el IdCategoria de la nueva categoría
    });
});


describe("GET /api/categorias/:id", function () {
    it("respond with json containing a single categorias", async function () {
        const res = await request(app)
            .get(`/api/categorias/${categoriaId}`);
        expect(res.statusCode).toEqual(200);
        expect(res.body).toEqual(
            expect.objectContaining({
                IdCategoria: categoriaId,
                Nombre: expect.any(String),
            })
        );
    });
});

describe("PUT /api/categorias/:id", function () {
    it("Debería actualizar una categoría existente", async function () {
        const actualizacionCategoria = {
            Nombre: "CATEGORIA ACTUALIZADA"
        };
        const res = await request(app)
            .put(`/api/categorias/${categoriaId}`)
            .send(actualizacionCategoria)
            .set("content-type", "application/json");
        expect(res.statusCode).toEqual(200);
        expect(res.body).toEqual({ message: 'Categoría actualizada' });
    });
});

describe("DELETE /api/categorias/:id", function () {
    it("Debería eliminar una categoría existente", async function () {
        const res = await request(app)
            .delete(`/api/categorias/${categoriaId}`)
            .set("content-type", "application/json");
        expect(res.statusCode).toEqual(200);
        expect(res.body).toEqual({ message: 'Categoría eliminada' });
    });
});

//solo se testean los métodos GET; 
//el primero testea la webapi de categorías y verifica que la respuesta sea un array con objetos que contengan los atributos IdCategoria y Nombre. 
//El segundo testea la webapi de categorias/:id y verifica que la respuesta sea un objeto que contenga los atributos IdCategoria = 1 y Nombre sea un texto. Este endpoint estaba propuesto en un ejercicio opcional, sino lo desarrolló le dará error.

//Implemente los test para métodos faltantes de la webapi de categorías.

