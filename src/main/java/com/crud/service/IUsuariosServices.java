package com.crud.service;

import java.util.List;

import com.crud.model.Usuario;

import org.springframework.data.domain.Example;

public interface IUsuariosServices {

    List <Usuario> buscarTodos();
    void guardar(Usuario us);
    void eliminar(int us);  
    Usuario buscarId(int id);
    List<Usuario> buscarByExample(Example<Usuario> example);
    
}
