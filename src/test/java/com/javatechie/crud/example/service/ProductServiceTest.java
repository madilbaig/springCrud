package com.javatechie.crud.example.service;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductServiceTest {

    @MockBean
    private ProductRepository repository;
    @Autowired
    private ProductService service;

    @Test
    void canFindAllProducts() {
        Product mockProduct = new Product(1,"abc", 2,234D);
        Product mockProduct1 = new Product(2,"abc1", 3,235D);
        doReturn(Arrays.asList(mockProduct,mockProduct1)).when(repository).findAll();

        List<Product> returnedProducts = service.retrieveProducts();

        Assertions.assertEquals(2,returnedProducts.size(),"findall should return 2 products");

    }

    @Test
    void canFindProductById() {
        Product mockProduct = new Product(1,"abc", 2,234D);
        doReturn(Optional.of(mockProduct)).when(repository).findById(1);

        Optional<Product> returnedProduct = service.retrieveProductById(1);

        Assertions.assertTrue(returnedProduct.isPresent(),"Product was not found");
        Assertions.assertSame(returnedProduct.get(),mockProduct,"Products should be same");
//        System.out.println("found by Id");
    }

    @Test
    void canFindProductByIdWhenNotFound(){
        Product mockProduct = new Product(1,"abc", 2,234D);
        doReturn(Optional.of(mockProduct)).when(repository).findById(1);

        Optional<Product> returnedProduct = service.retrieveProductById(2);

        Assertions.assertFalse(returnedProduct.isPresent(), "Product was found when it shouldn't be");
    }

    @Test
    void canAddProduct() {
        Product mockProduct = new Product(1,"abc", 2,234D);
        doReturn(mockProduct).when(repository).save(any());

        Product returnedProduct = service.saveProduct(mockProduct);

        Assertions.assertNotNull(returnedProduct,"The saved product should not be null");
        Assertions.assertEquals(1,returnedProduct.getId().intValue(),"The id for the new product should be 1");

    }

    @Test
    void canAddProducts() {
        Product mockProduct = new Product(1,"abc", 2,234D);
        Product mockProduct1 = new Product(2,"abc1", 3,235D);
        doReturn(Arrays.asList(mockProduct,mockProduct1)).when(repository).saveAll(anyList());

        List<Product> returnedProducts = service.saveProducts(Arrays.asList(mockProduct,mockProduct1));

        Assertions.assertNotNull(returnedProducts,"The saved product should not be null");
        Assertions.assertEquals(2,returnedProducts.size(),"saveall should return 2 products");

    }

    @Test
    void canDeleteProductById() {
        String result = service.removeProductById(1);

        Assertions.assertTrue(result.startsWith("product deleted"),"product not deleted");
    }

    @Test
    void canUpdateProduct() {
        Product mockProduct = new Product(1,"abc", 2,234D);
        doReturn(Optional.of(mockProduct)).when(repository).findById(1);

        Product returnedProduct = service.rebuildProduct(mockProduct);

        Assertions.assertEquals(returnedProduct,mockProduct,"not same");
        Assertions.assertNotNull(returnedProduct,"Should not be null");

    }
}

//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//class ProductServiceTest {
//
//    @Mock
//    private ProductRepository productRepository;
//    private ProductService UnderTest;
//
//    @BeforeEach
//    @Disabled
//    void setUp() {
//        UnderTest = new ProductService(productRepository);
//    }
//
//    @Test
//    @Disabled
//    void canSaveProduct() {
//        Product product = new Product("Monitor", 1, 12345D);
//        UnderTest.saveProduct(product);
//
//        ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);
//        verify(productRepository).save(productArgumentCaptor.capture());
//        Product capturedProduct = productArgumentCaptor.getValue();
//        assertThat(capturedProduct).isEqualTo(product);
//    }
//
//    @Test
//    @Disabled
//    void saveProducts() {
//        List<Product> products = new ArrayList<>();
//        Collections.addAll(products, new Product("Phone", 1, 12345D), new Product("Desk", 2, 4000D));
//        UnderTest.saveProducts(products);
//
//        ArgumentCaptor<ArrayList<Product>> productArgumentCaptor = ArgumentCaptor.forClass(ArrayList.class);
//        verify(productRepository).saveAll(productArgumentCaptor.capture());
//        List<Product> capturedProducts = productArgumentCaptor.getValue();
//        assertThat(capturedProducts).isEqualTo(products);
//    }
//}
//    @Test
//    public void testLeadMatches() throws Exception {
//        mockCrmObjectResponse();
//        mockAPIresponse();
//        mockLeadQueryResponse();
//        CRMAuthContext authContext = new CRMAuthContext();
//        authContext = authContext.withCrmDeployId(1).withEnvironmentType(1).withCrmEntityType(APIConstants.LEAD).withCrmObject(CRMObject.LEAD);
//        when(orgAccessTokenServiceImpl.fetchAuthContext(Mockito.any())).thenReturn(authContext);
//        EntityMatchesDTO entityMatchesDTO = new EntityMatchesDTO();
//        Set<Integer> empIds = new HashSet<>();
//        empIds.add(10);
//        entityMatchesDTO.setEmpIds(empIds );
//        MvcResult result = this.mvc
//                .perform(MockMvcRequestBuilders
//                        .post("/v5/crms/orgUUID/lead/search").("product", "iroc")param
//                .content(getGson().toJson(entityMatchesDTO)).contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andReturn();
//        String responseString = result.getResponse().getContentAsString();
////		List<Map<String,Object>> list = new ObjectMapper().readValue(responseString, new org.codehaus.jackson.type.TypeReference<List<Map<String,Object>>>() {
////		});
////
//        List<IVEntityMatchResponseDTO<CRMLead>> list = gson.fromJson(responseString, new TypeToken<List<IVEntityMatchResponseDTO<CRMLead>>>(){}.getType());
//        list.stream().forEach(item->{
//            if((Integer) item.getEmpId()==1) {
//                List<CRMLead> crmResponse = item.getCrmMatchResponse();
//                crmResponse.stream().forEach(lead->{
//                    assertEquals("company1",lead.getCompanyName());
//                    assertEquals("company1",lead.getLastName());
//                    assertEquals("email",lead.getEmail());
//                });
//            }else if((Integer) item.getEmpId()==2) {
//                List<CRMLead> crmResponse = item.getCrmMatchResponse();
//                crmResponse.stream().forEach(lead->{
//                    assertEquals("company2",lead.getCompanyName());
//                    assertEquals("company",lead.getLastName());
//                    assertEquals("email2",lead.getEmail());
//                });
//            }else if((Integer) item.getEmpId()==10) {
//
//            }else {
//                assertTrue(false);
//            }
//        });
//
//        ArgumentCaptor<String> query = ArgumentCaptor.forClass(String.class);
//        Mockito.verify(sfAccountService, Mockito.atLeast(1)).querySalesForce(any(),query.capture(), any(),any());
//        query.getAllValues().stream().forEach(string->{
//            assertTrue(getBalancesQuery(string));
//        });
//    }
