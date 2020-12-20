package ru.tecom.test.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.tecom.test.demo.entity.Car;
import ru.tecom.test.demo.exporter.CarExcelExporter;
import ru.tecom.test.demo.service.CarService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static ru.tecom.test.demo.cosntants.CarSearchFieldKeys.*;

@Controller
@RequestMapping("/api")
public class CarController {

    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/cars/new")
    public String getNew(Model model){
        model.addAttribute("car", new Car());
        return "cars/new";
    }

    @PostMapping("/cars/save")
    public String saveEmployee(@Valid @ModelAttribute("car") Car car, BindingResult result){
        if(result.hasErrors()) return "cars/new";
        service.save(car);
        return "redirect:/api/cars";
    }

    @GetMapping("/cars/update")
    public String showFormForUpdate(@RequestParam("carId") int id, Model model){
        Car car = service.findById(id);
        model.addAttribute("car", car);
        return "cars/new";
    }

    @GetMapping("/cars/delete")
    public String delete(@RequestParam("carId") int id){
        service.deleteById(id);
        return "redirect:/api/cars";
    }

    // This is a bad approach. It was used only for Demo
    // Better to use one RequestParam for custom search, concat necessary values and later parse them in service
    @GetMapping("/cars")
    public String getCarsByParameters(@RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                      @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                      @RequestParam(value = "rB", defaultValue = "") String requestedBrand,
                                      @RequestParam(value = "rM", defaultValue = "") String requestedModel,
                                      @RequestParam(value = "rY", defaultValue = "") String requestedYear,
                                      @RequestParam(value = "rTurbo", defaultValue = "") String requestedTurbo,
                                      @RequestParam(value = "rColor", defaultValue = "") String requestedColor,
                                      Model model){

        Map<String, String> parameters  = new HashMap<>();
        parameters.put(SORT_FIELD, sortField);
        parameters.put(SORT_DIRECTION, sortDirection);
        parameters.put(REQ_BRAND, requestedBrand);
        parameters.put(REQ_MODEL, requestedModel);
        parameters.put(REQ_YEAR_EQ, requestedYear);
        parameters.put(REQ_TURBO, requestedTurbo);
        parameters.put(REQ_COLOR, requestedColor);

        model.addAttribute("cars", service.findAllByParameters(parameters));

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("rB", requestedBrand);
        model.addAttribute("rM", requestedModel);
        model.addAttribute("rY", requestedYear);
        model.addAttribute("rTurbo", requestedTurbo);
        model.addAttribute("rColor", requestedColor);

        return "cars/list";
    }

    @GetMapping("/cars/export/excel")
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                              @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                              @RequestParam(value = "rB", defaultValue = "") String requestedBrand,
                              @RequestParam(value = "rM", defaultValue = "") String requestedModel,
                              @RequestParam(value = "rY", defaultValue = "") String requestedYear,
                              @RequestParam(value = "rTurbo", defaultValue = "") String requestedTurbo,
                              @RequestParam(value = "rColor", defaultValue = "") String requestedColor) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=cars_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        Map<String, String> parameters  = new HashMap<>();
        parameters.put(SORT_FIELD, sortField);
        parameters.put(SORT_DIRECTION, sortDirection);
        parameters.put(REQ_BRAND, requestedBrand);
        parameters.put(REQ_MODEL, requestedModel);
        parameters.put(REQ_YEAR_EQ, requestedYear);
        parameters.put(REQ_TURBO, requestedTurbo);
        parameters.put(REQ_COLOR, requestedColor);

        List<Car> carList = service.findAllByParameters(parameters);

        CarExcelExporter excelExporter = new CarExcelExporter(carList);

        excelExporter.export(response);
    }
}
