package com.example.crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Component
public class Vezerlo {
    @Autowired
    private DolgRepo dolgRepo;

    @GetMapping("/")
    public String Fooldal(Model model, String uzenet) {
        model.addAttribute("dolgozok", dolgRepo.findAll());
        model.addAttribute("uzenet", model.getAttribute("uzenet"));
        return "index";
    }

    @GetMapping("/uj")
    public String UjDolgozoOldal(Model model) {
        model.addAttribute("dolgozo", new Dolgozo());
        return "ujdolgozo";
    }

    @PostMapping(value = "/ment")
    public String mentDolgozo(@ModelAttribute Dolgozo dolgozo, RedirectAttributes redirAttr) {
        for(Dolgozo dolgozo2: dolgRepo.findAll())
            if(dolgozo2.getNev().equals(dolgozo.getNev()) && dolgozo2.getCim().equals(dolgozo.getCim())){
                redirAttr.addFlashAttribute("uzenet","Ezzel a névvel és címmel már van dolgozó. ID="+dolgozo2.getId());
                return "redirect:/";
            }
        dolgRepo.save(dolgozo);
        redirAttr.addFlashAttribute("uzenet","Új dolgozó hozzá lett adva! ID="+dolgozo.getId());
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String modositDolgozo(@PathVariable(name = "id") int id, Model model) {
        model.addAttribute("dolgozo", dolgRepo.findById(id));
        return "modosit";
    }

    @PostMapping(value = "/modosit")
    public String modositDolgozo(@ModelAttribute Dolgozo dolgozo, RedirectAttributes redirAttr) {
        dolgRepo.save(dolgozo);
        redirAttr.addFlashAttribute("uzenet","Dolgozó módosítva! ID="+dolgozo.getId());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String torolDolgozo(@PathVariable(name = "id") int id, RedirectAttributes redirAttr) {
        redirAttr.addFlashAttribute("uzenet","Dolgozó törölve! ID="+dolgRepo.findById(id).get().getId());
        dolgRepo.delete(dolgRepo.findById(id).get());
        return "redirect:/";
    }
}
