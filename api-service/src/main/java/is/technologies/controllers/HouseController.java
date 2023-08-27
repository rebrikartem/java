package is.technologies.controllers;

import is.technologies.dto.CreateHouseDto;
import is.technologies.dto.HouseDto;
import is.technologies.services.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/house")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping
    public List<HouseDto> findAll() {
        return houseService.findAll();
    }

    @GetMapping(value = "/{id}")
    public HouseDto findById(@PathVariable("id") long id) {
        return houseService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HouseDto create(@RequestBody CreateHouseDto houseDto) {
        return houseService.create(houseDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        houseService.delete(id);
    }
}
