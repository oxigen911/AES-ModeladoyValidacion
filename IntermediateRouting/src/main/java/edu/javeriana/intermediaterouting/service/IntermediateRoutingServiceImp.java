package edu.javeriana.intermediaterouting.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.ClientResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.javeriana.intermediaterouting.exception.ResourceNotFoundException;
import edu.javeriana.intermediaterouting.model.Convenio;
import edu.javeriana.intermediaterouting.model.FuncionEnum;
import edu.javeriana.intermediaterouting.model.Pago;
import edu.javeriana.intermediaterouting.model.RESTData;
import edu.javeriana.intermediaterouting.model.SOAPData;
import edu.javeriana.intermediaterouting.utils.utils.RESTInvoker;
import edu.javeriana.intermediaterouting.utils.utils.SOAPInvoker;
import lombok.NoArgsConstructor;

@SuppressWarnings("unchecked")
@Service
@NoArgsConstructor
public class IntermediateRoutingServiceImp extends IntermediateRoutingService {
   /* configuracion conexion ws RegistryService */
   private static final String TS_SERVER = "localhost";
   private static final int TS_PORT = 8081;
   private static final String TS_CAPACITY = "api/v1/convenio/";
   private static String TS_URL = String.format("http://%s:%d/%s", TS_SERVER, TS_PORT, TS_CAPACITY);

   public String pagarFactura(String idFactura, Double valorPago) {
      /*
       * tomamos los 1 primeros digitos de la factura para buscarlo en el listado de convenios
       */
      Long llaveConvenio = Long.parseLong(idFactura.substring(0, 1));
      /* valida la existencia del convenio */
      Convenio convenio = validarExistenciaConvenio(llaveConvenio);
      if (convenio != null && convenio.getId() != null) {
         /*
          * aca debe consumir el convenio y obetener el dato de la factura y de volverlo como el objeto Pago
          */
         String respuesta = consumirWSConvenio(convenio, llaveConvenio, idFactura, valorPago, FuncionEnum.PAGAR);
         System.out.println(" pagar>>>>>>>>>>>>>>>>< " + respuesta);
         return respuesta;
      }
      return "-1";
   }

   public Pago getInfoFactura(String idFactura) {
      Long llaveConvenio = Long.parseLong(idFactura.substring(0, 1));
      Convenio convenio = validarExistenciaConvenio(llaveConvenio);
      if (convenio != null && convenio.getId() != null) {
         Pago response = consumirWSConvenioConsultar(convenio, llaveConvenio, idFactura,
               llaveConvenio + "-" + convenio.getNombre());
         return response;
      }
      return new Pago();
   }

   public String doCompensarFactura(String idFactura, Double valorPago) {
      /*
       * tomamos los 4 primeros digitos de la factura para buscarlo en el listado de convenios
       */
      Long llaveConvenio = Long.parseLong(idFactura.substring(0, 1));
      /* valida la existencia del convenio */
      Convenio convenio = validarExistenciaConvenio(llaveConvenio);
      if (convenio != null && convenio.getId() != null) {
         /*
          * aca debe consumir el convenio y obetener el dato de la factura y de volverlo como el objeto Pago
          */
         String respuesta = consumirWSConvenio(convenio, llaveConvenio, idFactura, valorPago, FuncionEnum.COMPENSAR);
         System.out.println(" compensar>>>>>>>>>>>>>>>>< " + respuesta);
         return respuesta;
      }
      return "-1";
   }

   public Convenio validarExistenciaConvenio(Long idConvenio) {

      Client client = Client.create();
      ClientResponse response = client.resource(TS_URL + idConvenio)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).get(ClientResponse.class);
      ObjectMapper objectMapper = new ObjectMapper();

      // Convenio convenio = objectMapper.readValue(response.getEntity(String.class), Convenio.class);
     Convenio convenio = null;
      try {
         ObjectMapper mapper = new ObjectMapper();
         StringBuilder primero = new StringBuilder();
         primero.append(
               "{\"id\":1,\"idConvenio\":1234,\"nombre\":\"colpatria\",\"host\":\"192.168.99.100\",\"puerto\":9090,\"urlServicio\":\"servicios/pagos/v1\",\"tipo\":\"REST\",\"restServicesData\":[{\"metodo\":\"GET\",\"funcion\":\"CONSULTAR\",\"recurso\":\"payments/{idFactura}\",\"paramsMapping\":\"idFactura:numeroFactura\",\"payloadMapping\":\"valorFactura:totalPago\",\"headers\":\"\",\"responseData\":\"valorFactura\",\"accept\":\"application/json\"},{\"metodo\":\"POST\",\"funcion\":\"PAGAR\",\"recurso\":\"payments/{idFactura}\",\"paramsMapping\":\"idFactura:numeroFactura\",\"payloadMapping\":\"valorFactura:totalPago\",\"headers\":\"Accept:application/json,Content-Type:application/json;charset=utf8\",\"responseData\":\"mensaje\",\"accept\":\"application/json\"},{\"metodo\":\"DELETE\",\"funcion\":\"COMPENSAR\",\"recurso\":\"payments/{idFactura}\",\"paramsMapping\":\"idFactura:numeroFactura\",\"payloadMapping\":\"valorFactura:totalPago\",\"headers\":\"Accept:application/json,Content-Type:application/json;charset=utf8\",\"responseData\":\"mensaje\",\"accept\":\"application/json\"}],\"soapServicesData\":[]}");
         convenio = mapper.readValue(primero.toString().trim(), Convenio.class);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return convenio;
   }

   public String consumirWSConvenio(Convenio convenio, Long idConvenio, String idFactura, Double valorPago,
         FuncionEnum opcion) {
      /* objeto de consulta */
      Pago pago = new Pago();
      pago.setNumeroConvenio(idConvenio);
      pago.setNumeroFactura(Long.parseLong(idFactura));
      pago.setTotalPago(valorPago);
      String endpointUrl = String.format("http://%s:%d/%s", convenio.getHost(), convenio.getPuerto(),
            convenio.getUrlServicio());
      /* determian el tipo de servicio a consumir - REST/SOAP */
      if (convenio.getTipo().equals("REST")) {
         RESTData serviceData = convenio.getRESTDataByFuncion(opcion);
         System.out.println(endpointUrl + "/" + serviceData.getRecurso() + "----" + idFactura + "----$--" + valorPago);
         System.out.println("---------A > " + serviceData.getMetodo());
         return (String) RESTInvoker.invokeService(endpointUrl + "/" + serviceData.getRecurso(),
               serviceData.getMetodo(), serviceData.getParamsMapping(), serviceData.getHeaders(),
               serviceData.getAccept(), serviceData.getPayloadMapping(), serviceData.getResponseData(), pago);
      } else {
         SOAPData serviceData = convenio.getSOAPDataByFuncion(opcion);
         return (String) SOAPInvoker.invokeService(endpointUrl, serviceData.getSoapAction(),
               serviceData.getXsltDefinition(), serviceData.getResponseElement(), pago);
      }
   }

   public Pago consumirWSConvenioConsultar(Convenio convenio, Long idConvenio, String idFactura, String opcion) {
      /* objeto de consulta */
      Pago pago = new Pago();
      pago.setNumeroConvenio(idConvenio);
      pago.setNumeroFactura(Long.parseLong(idFactura));
      pago.setNumeroIdCliente(opcion);
      String endpointUrl = String.format("http://%s:%d/%s", convenio.getHost(), convenio.getPuerto(),
            convenio.getUrlServicio());
      /* determian el tipo de servicio a consumir - REST/SOAP */
      if (convenio.getTipo().equals("REST")) {
         RESTData serviceData = convenio.getRESTDataByFuncion(FuncionEnum.CONSULTAR);
         System.out.println(endpointUrl + "/" + serviceData.getRecurso() + "----" + idFactura);
         System.out.println("---------A > " + serviceData.getMetodo());

         pago.setTotalPago((Double) RESTInvoker.invokeService(endpointUrl + "/" + serviceData.getRecurso(),
               serviceData.getMetodo(), serviceData.getParamsMapping(), serviceData.getHeaders(),
               serviceData.getAccept(), serviceData.getPayloadMapping(), serviceData.getResponseData(), pago));
      } else {
         SOAPData serviceData = convenio.getSOAPDataByFuncion(FuncionEnum.CONSULTAR);
         pago.setTotalPago((Double) SOAPInvoker.invokeService(endpointUrl, serviceData.getSoapAction(),
               serviceData.getXsltDefinition(), serviceData.getResponseElement(), pago));
      }
      /* si el consumir el ws es valido, retorna el valor */
      if (pago.getTotalPago() != null) {
         return pago;
      }
      return new Pago();
   }

}
