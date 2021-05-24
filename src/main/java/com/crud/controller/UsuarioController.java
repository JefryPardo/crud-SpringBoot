package com.crud.controller;

import java.util.List;

import com.crud.model.Usuario;
import com.crud.service.IUsuariosServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    
    @Autowired
    private IUsuariosServices servicesUsuario;

    @GetMapping("/")
    public String AllUsers(Model m){        
        return "home";
    }

    @GetMapping("/nuevo")
    public String FormCreate(Usuario us){
        return "create";
    }

    


    @PostMapping("/agregar")
    public String create(Usuario us, BindingResult r, RedirectAttributes m){

        if(r.hasErrors()){
            return "create";
        }

        servicesUsuario.guardar(us);
        m.addFlashAttribute("msg", "Se creo el usuario");

        return "redirect:/usuario/";
    }

    @GetMapping("/edit/{id}")
    public String editarId(@PathVariable("id") int id, Model m){
        Usuario us = servicesUsuario.buscarId(id);
        m.addAttribute("usuario", us);
        return "/create";
    }

    @GetMapping("/delete/{id}")
    public String borrarId(@PathVariable("id") int id, RedirectAttributes ms){        
        servicesUsuario.eliminar(id);
        ms.addFlashAttribute("msg", "Se borro correctamente.");
        return "redirect:/usuario/";
    }




    //Buscar metodos usados 
    //Este metodo buscar los campos diferentes a nulos 
    @GetMapping("/buscar")
    public String buscar(@ModelAttribute("buscar") Usuario us, Model m){
        System.out.println("AQUIII: "+us);

        //Esta linea es para no usar el operador = y en su caso usar el operador LIKE
        ExampleMatcher mar = ExampleMatcher.matching().withMatcher("nombre",ExampleMatcher.GenericPropertyMatchers.contains());

        Example<Usuario> example = Example.of(us,mar);
        List<Usuario> list = servicesUsuario.buscarByExample(example);
        m.addAttribute("usuarios", list);
        return "home";
    }

    @ModelAttribute
	public void setGenericos(Model modelo){
        Usuario us = new Usuario();
		modelo.addAttribute("usuarios", servicesUsuario.buscarTodos());
        modelo.addAttribute("buscar", us);
	}

    //Este metodo pasa los valores vacios a null para asi evitar en el Data Binding  buscar valores iguales a vacios
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    








}
