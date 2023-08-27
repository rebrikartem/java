package is.technologies.controllers;

import is.technologies.dto.CreateStreetDto;
import is.technologies.dto.StreetDto;
import is.technologies.services.StreetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/street")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequiredArgsConstructor
public class StreetController {

    private final StreetService streetService;

    @GetMapping
    public List<StreetDto> findAll() {
        return streetService.findAll();
    }

    @GetMapping(value = "/{id}")
    public StreetDto findById(@PathVariable("id") Long id) {
        return streetService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StreetDto create(@RequestBody CreateStreetDto streetDto) {
        return streetService.create(streetDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StreetDto update(@RequestBody StreetDto streetDto) {
        return streetService.update(streetDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        streetService.delete(id);
    }
}
