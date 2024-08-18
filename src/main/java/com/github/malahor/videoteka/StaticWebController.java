package com.github.malahor.videoteka;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class StaticWebController {

  @GetMapping("/search")
  public ModelAndView searchPage() {
    return new ModelAndView("forward:/");
  }

  @GetMapping("/movies")
  public ModelAndView moviesPage() {
    return new ModelAndView("forward:/");
  }

  @GetMapping("/series")
  public ModelAndView seriesPage() {
    return new ModelAndView("forward:/");
  }
}
