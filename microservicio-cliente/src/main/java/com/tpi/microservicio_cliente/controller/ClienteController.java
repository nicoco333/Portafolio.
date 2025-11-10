package com.tpi.microservicio_cliente.controller;

import com.tpi.microservicio_cliente.entity.Cliente;
import com.tpi.microservicio_cliente.entity.Contenedor;
import com.tpi.microservicio_cliente.entity.SolicitudTraslado;
import com.tpi.microservicio_cliente.entity.enums.EstadoSolicitud;
import com.tpi.microservicio_cliente.repository.ClienteRepository;
import com.tpi.microservicio_cliente.repository.ContenedorRepository;
import com.tpi.microservicio_cliente.repository.SolicitudTrasladoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List; 
import java.util.Arrays; 
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cliente") // URL base para este controlador
public class ClienteController {

    // Inyectamos los 3 repositorios
    private final ClienteRepository clienteRepository;
    private final ContenedorRepository contenedorRepository;
    private final SolicitudTrasladoRepository solicitudTrasladoRepository;

    // Inyección de dependencias por constructor
    public ClienteController(ClienteRepository clienteRepository,
                             ContenedorRepository contenedorRepository,
                             SolicitudTrasladoRepository solicitudTrasladoRepository) {
        this.clienteRepository = clienteRepository;
        this.contenedorRepository = contenedorRepository;
        this.solicitudTrasladoRepository = solicitudTrasladoRepository;
    }

    /**
     * Endpoint para registrar un nuevo cliente.
     * HTTP POST a /api/cliente/clientes
     * (Basado en el requisito [POST /clientes] de la entrega inicial)
     */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    /**
     * Endpoint para crear una nueva solicitud de traslado con su contenedor.
     * HTTP POST a /api/cliente/solicitudes
     * (Basado en el requisito [POST /solicitudes] de la entrega inicial)
     */
    @PostMapping("/solicitudes")
    public ResponseEntity<SolicitudTraslado> crearSolicitud(@RequestBody SolicitudTraslado solicitud) {
        // Asumimos que el JSON viene con el contenedor anidado y el ID del cliente
        // Ejemplo de JSON:
        // {
        //   "cliente": { "id": 1 },
        //   "contenedor": {
        //     "identificacionUnica": "CONT-999",
        //     "pesoKg": 5000.0,
        //     "volumenM3": 30.0
        //   }
        // }

        // 1. Ponemos el estado inicial
        solicitud.setEstado(EstadoSolicitud.BORRADOR);

        // 2. Guardamos la solicitud.
        // Gracias a "CascadeType.ALL" en la entidad SolicitudTraslado,
        // esto guardará el nuevo Contenedor automáticamente.
        SolicitudTraslado nuevaSolicitud = solicitudTrasladoRepository.save(solicitud);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    /**
     * Endpoint para ver el estado de una solicitud.
     * HTTP GET a /api/cliente/solicitudes/{id}
     * (Basado en el requisito [GET /solicitudes/{id}] de la entrega inicial)
     */
    @GetMapping("/solicitudes/{id}")
    public ResponseEntity<SolicitudTraslado> verEstadoSolicitud(@PathVariable Long id) {
        Optional<SolicitudTraslado> solicitudOptional = solicitudTrasladoRepository.findById(id);

        if (solicitudOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(solicitudOptional.get());
    }
    @GetMapping("/solicitudes")
    public ResponseEntity<List<SolicitudTraslado>> listarTodasLasSolicitudes() {
        List<SolicitudTraslado> solicitudes = solicitudTrasladoRepository.findAll();
        return ResponseEntity.ok(solicitudes);
    }

    /**
     * [ADMIN] Endpoint para filtrar contenedores pendientes.
     * HTTP GET a /api/cliente/contenedores/pendientes
     * (Basado en el requisito [GET /contenedores/estado=pendiente] de la entrega inicial)
     */
    @GetMapping("/contenedores/pendientes")
    public ResponseEntity<List<Contenedor>> filtrarContenedoresPendientes() {
        
        // 1. Definimos qué significa "pendiente"
        List<EstadoSolicitud> estadosPendientes = Arrays.asList(
                EstadoSolicitud.BORRADOR, 
                EstadoSolicitud.PROGRAMADA
        );

        // 2. Buscamos las SOLICITUDES que están pendientes
        List<SolicitudTraslado> solicitudesPendientes = 
                solicitudTrasladoRepository.findByEstadoIn(estadosPendientes);

        // 3. Extraemos los CONTENEDORES de esas solicitudes
        List<Contenedor> contenedoresPendientes = solicitudesPendientes.stream()
                .map(SolicitudTraslado::getContenedor) // Obtiene el contenedor de cada solicitud
                .collect(Collectors.toList()); // Los junta en una nueva lista

        return ResponseEntity.ok(contenedoresPendientes);
    }
}