package com.crud.service.db;

import java.util.List;
import java.util.Optional;

import com.crud.model.Usuario;
import com.crud.repository.UsuarioRepository;
import com.crud.service.IUsuariosServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UsuariosServicesJpa implements IUsuariosServices{

    @Autowired
    private UsuarioRepository usuarioRepo;


    @Override
    public List<Usuario> buscarTodos() {
        return usuarioRepo.findAll();
    }

    

    @Override
    public void guardar(Usuario us) {
       usuarioRepo.save(us);
    }

    @Override
    public void eliminar(int us) {
        usuarioRepo.deleteById(us);        
    }



    @Override
    public Usuario buscarId(int id) {
        Optional<Usuario> us = usuarioRepo.findById(id);
        if(us.isPresent()){
            return us.get();
        }
        return null;
    }



    @Override
    public List<Usuario> buscarByExample(Example<Usuario> example) {
        return usuarioRepo.findAll(example);
    }
    
}
