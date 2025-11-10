package com.tpi.microservicioflota.controller;

import com.tpi.microservicioflota.entity.Camion;
import com.tpi.microservicioflota.entity.Tramo; // <-- NUEVA IMPORTACIÓN
import com.tpi.microservicioflota.entity.Transportista;
import com.tpi.microservicioflota.entity.enums.EstadoTramo; // <-- NUEVA IMPORTACIÓN
import com.tpi.microservicioflota.repository.CamionRepository;
import com.tpi.microservicioflota.repository.TramoRepository; // <-- NUEVA IMPORTACIÓN
import com.tpi.microservicioflota.repository.TransportistaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime; // <-- NUEVA IMPORTACIÓN
import java.util.List;
import java.util.Optional; // <-- NUEVA IMPORTACIÓN

@RestController
@RequestMapping("/api/flota")
public class FlotaController {

    private final CamionRepository camionRepository;
    private final TransportistaRepository transportistaRepository;
    private final TramoRepository tramoRepository; // <-- 1. AÑADIDO NUEVO REPO

    // 2. CONSTRUCTOR MODIFICADO
    public FlotaController(CamionRepository camionRepository,
                           TransportistaRepository transportistaRepository,
                           TramoRepository tramoRepository) { // <-- Añadido aquí
        this.camionRepository = camionRepository;
        this.transportistaRepository = transportistaRepository;
        this.tramoRepository = tramoRepository; // <-- Añadido aquí
    }

    // --- MÉTODOS QUE YA TENÍAS ---

    @PostMapping("/transportistas")
    public ResponseEntity<Transportista> registrarTransportista(@RequestBody Transportista transportista) {
        Transportista nuevoTransportista = transportistaRepository.save(transportista);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTransportista);
    }

    @PostMapping("/camiones")
    public ResponseEntity<Camion> registrarCamion(@RequestBody Camion camion) {
        Camion nuevoCamion = camionRepository.save(camion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCamion);
    }

    @GetMapping("/camiones/disponibles")
    public ResponseEntity<List<Camion>> obtenerCamionesDisponibles() {
        List<Camion> camionesDisponibles = camionRepository.findByDisponibleTrue();
        return ResponseEntity.ok(camionesDisponibles);
    }

    // --- NUEVO MÉTODO AÑADIDO ---

    /**
     * Endpoint para registrar el inicio de un tramo.
     * HTTP PUT a /api/flota/tramos/{id}/inicio
     * Rol: Transportista
     * * @param id El ID del tramo que se inicia.
     * @return El tramo actualizado.
     */
    @PutMapping("/tramos/{id}/inicio")
    public ResponseEntity<Tramo> registrarInicioTramo(@PathVariable Long id) {
        
        // 1. Buscar el tramo por ID
        Optional<Tramo> tramoOptional = tramoRepository.findById(id);
        
        if (tramoOptional.isEmpty()) {
            // Si no se encuentra el tramo, devolver 404 Not Found
            return ResponseEntity.notFound().build();
        }

        Tramo tramo = tramoOptional.get();

        // 2. Actualizar el tramo
        tramo.setEstado(EstadoTramo.INICIADO);
        tramo.setFechaHoraInicio(LocalDateTime.now());

        // 3. (Lógica de negocio) Poner el camión asignado como "no disponible"
        Camion camionAsignado = tramo.getCamion();
        if (camionAsignado != null) {
            camionAsignado.setDisponible(false);
            camionRepository.save(camionAsignado);
        }

        // 4. Guardar el tramo actualizado
        Tramo tramoActualizado = tramoRepository.save(tramo);

        // 5. Devolver 200 OK con el tramo actualizado
        return ResponseEntity.ok(tramoActualizado);
    }
    /**
     * Endpoint para registrar el fin de un tramo.
     * HTTP PUT a /api/flota/tramos/{id}/fin
     * Rol: Transportista
     * * @param id El ID del tramo que se finaliza.
     * @return El tramo actualizado.
     */
    @PutMapping("/tramos/{id}/fin")
    public ResponseEntity<Tramo> registrarFinTramo(@PathVariable Long id) {
        
        // 1. Buscar el tramo por ID
        Optional<Tramo> tramoOptional = tramoRepository.findById(id);
        
        if (tramoOptional.isEmpty()) {
            // Si no se encuentra el tramo, devolver 404 Not Found
            return ResponseEntity.notFound().build();
        }

        Tramo tramo = tramoOptional.get();

        // 2. Actualizar el tramo
        tramo.setEstado(EstadoTramo.FINALIZADO);
        tramo.setFechaHoraFin(LocalDateTime.now());

        // 3. (Lógica de negocio) Poner el camión asignado como "disponible"
        Camion camionAsignado = tramo.getCamion();
        if (camionAsignado != null) {
            camionAsignado.setDisponible(true); // El camión queda libre
            camionRepository.save(camionAsignado);
        }

        // 4. Guardar el tramo actualizado
        Tramo tramoActualizado = tramoRepository.save(tramo);

        // 5. Devolver 200 OK con el tramo actualizado
        return ResponseEntity.ok(tramoActualizado);
    }
}