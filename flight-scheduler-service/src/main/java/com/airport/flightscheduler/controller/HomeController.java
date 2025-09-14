@Controller
public class HomeController {

    @GetMapping("/")
    public Map<String, String> index() {
        return Map.of(
                "message", "Welcome! Please use /flights or visit /swagger-ui.html for API documentation."
        );
    }
}