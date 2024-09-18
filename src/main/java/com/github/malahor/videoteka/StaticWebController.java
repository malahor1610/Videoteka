package com.github.malahor.videoteka;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class StaticWebController {

  @GetMapping("/")
  public String home() {
    return "index.html";
  }

  @GetMapping("/watchlist")
  public String watchlist() {
    return "watchlist.html";
  }

  @GetMapping("/search")
  public String searchPage() {
    return "search.html";
  }

  @GetMapping("/watchlist/movies")
  public String moviesPage() {
    return "movies.html";
  }

  @GetMapping("/watchlist/series")
  public String seriesPage() {
    return "series.html";
  }

  @GetMapping("/random")
  public String randomPage() {
    return "random.html";
  }
}
