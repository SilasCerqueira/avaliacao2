/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacao.controllers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.avaliacao.entity.ContaCorrente;
import br.com.avaliacao.infra.Generic;
import br.com.avaliacao.service.ContaBus;


@Path("contas")
public class ContaController extends Generic {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<ContaCorrente> listContas() {
        try {
            ContaBus contaBus = new ContaBus();
            return contaBus.listar();
        } catch (Exception ex) {
            Logger.getLogger(ContaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}/")
    public ContaCorrente getConta(@PathParam("id") long id) {
        try {
            ContaBus contaBus = new ContaBus();
            return contaBus.selecionar(id);
        } catch (Exception ex) {
            Logger.getLogger(ContaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response create(ContaCorrente conta) {
       try {
    	   ContaBus contaBus = new ContaBus();
    	   contaBus.inserir(conta);        	
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ContaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response update(ContaCorrente conta) {
        try {
            ContaBus contaBus = new ContaBus();
            contaBus.alterar(conta);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ContaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @DELETE
    @Path("{id}/")
    public Response delete(@PathParam("id") long id) {
        try {
            ContaBus contaBus = new ContaBus();
            contaBus.excluir(id);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            Logger.getLogger(ContaController.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
