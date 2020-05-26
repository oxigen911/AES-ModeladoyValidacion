# AES-ModeladoyValidacion
Taller1 de Modelado y Validacion
Nombres: Carlos Rodríguez
	     Alberto Zapata
	     Reynaldo Blanco
	     Juan Villanueva

Taller de modelado y validación de arquitectura

1. Patrón nuclear de la implementación: Microservicios

Como patrón nuclear de la arquitectura elegimos microservicios. Este nos permitirá un desacoplamiento de los sistemas que interactúan reduciendo la complejidad e incrementando la portabilidad y la escalabilidad del sistema. Esto es muy importante para nuestro diseño ya que nuestra idea requiere exponer una serie de servicios REST para la gestión de los convenio de pago que este disponga dentro de nuestra plataforma.
 
Dentro de las ventajas por las cuales lo seleccionamos como patrón nuclear están:
• Escalabilidad
• Funcionalidad modular, módulos independientes.
• Libertad del desarrollador de desarrollar y desplegar servicios de forma independiente
• Uso de contenedores permitiendo el despliegue y el desarrollo de la aplicación rápidamente
De la misma forma sabemos que encontramos desventajas en este enfoque como lo es la gestión de estos servicios y la complejidad que lleva realizar pruebas end to end.

2. Otros patrones implementados en la solución

• Cliente – servidor: La arquitectura cliente-servidor es un patrón de arquitectura de software en el que las tareas se reparten entre los proveedores de recursos o servicios (servidores), y los que solicitan estos servicios o recursos (clientes). 
• Orquestación de servicios: Se realiza una orquestación de servicios (composición de servicios) en el cual un servicio es el encargado de realizar de la interacción entre los diferentes servicios creados para cumplir con la funcionalidad.
• MVC: Patrón de arquitectura de software que utilizando 3 componentes separados (Vista, Modelo y Controlador) separa la lógica de la aplicación de la lógica de la vista en una aplicación donde el controlador permite la comunicación entre la vista y el modelo y comunica entre ellos los cambios de estado de cada uno.
• Dispatcher: Un dispatcher es el responsable de la ejecución de los servicios, controlando su ejecución de acuerdo con los parámetros enviados por el routing controller. Un dispatcher se puede encapsular dentro de un controlador (en este caso lo hemos implementado así) o puede ser un componente independiente que trabaja en coordinación con el controlador.
• Intermediate routing: Es un patrón que se usa para el enrutamiento de la petición al servicio correcto de acuerdo con los parámetros que se le ingresan.
• Registry service: Servicio para la ejecución de los pagos de acuerdo con el número de factura y el convenio al que pertenece la misma.
