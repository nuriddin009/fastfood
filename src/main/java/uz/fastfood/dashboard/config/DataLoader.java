package uz.fastfood.dashboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.fastfood.dashboard.entity.Branch;
import uz.fastfood.dashboard.repository.*;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public void run(String... args) throws Exception {


        if (branchRepository.count() == 0) {

            branchRepository.save(Branch.builder()
                            .landmark("")
                            .nameUz("")
                            .nameRu("")
                    .build());

        }


    }
}
