import "./App.css";
import React from "react";
import { BrowserRouter, Route, Routes, Navigate } from "react-router-dom";
import {Menu} from "./components/Menu";
import { Footer } from "./components/Footer";
import { Inicio } from "./components/Inicio";
import { Categorias } from "./components/Categorias";
import { Articulos } from "./components/articulos/Articulos";
function App() {
  return (
    <>
        <BrowserRouter>
          <Menu />
          <div className="divBody">
            <Routes>
              <Route path="/inicio" element={<Inicio />} />
              <Route
                path="/categorias"
                element={<Categorias />}
              />
              <Route path="*" element={<Navigate to="/inicio" replace />} />
              <Route path="/articulos" element={<Articulos/>} />
            </Routes>
          </div>
          <Footer />
        </BrowserRouter>
    </>
  );
}
export default App;
