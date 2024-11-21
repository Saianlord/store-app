package com.store.store_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class StoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreAppApplication.class, args);
	}
//	@Autowired
//	UserService service;
//
//	@Autowired
//	PasswordEncoder passwordEncoder;

//	@Bean
//	CommandLineRunner init(){
//
//		return args -> {
//			//Creating permissions
//
//			Permission createProducts = Permission.builder()
//					.name(PermissionEnum.CREATE_PRODUCTS).build();
//
//			Permission readProducts = Permission.builder()
//					.name(PermissionEnum.READ_PRODUCTS).build();
//
//			Permission updateProducts = Permission.builder()
//					.name(PermissionEnum.UPDATE_PRODUCTS).build();
//
//			Permission deleteProducts = Permission.builder()
//					.name(PermissionEnum.DELETE_PRODUCTS).build();
//
//			Permission createCategory = Permission.builder()
//					.name(PermissionEnum.CREATE_CATEGORY).build();
//
//			Permission readCategory = Permission.builder()
//					.name(PermissionEnum.READ_CATEGORY).build();
//
//			Permission updateCategory = Permission.builder()
//					.name(PermissionEnum.UPDATE_CATEGORY).build();
//
//			Permission deleteCategory = Permission.builder()
//					.name(PermissionEnum.DELETE_CATEGORY).build();
//
//			Permission createUserAdmin = Permission.builder()
//					.name(PermissionEnum.CREATE_USER_ADMIN).build();
//
//			Permission createUser = Permission.builder()
//					.name(PermissionEnum.CREATE_USER).build();
//
//			Permission readUser = Permission.builder()
//					.name(PermissionEnum.READ_USER).build();
//
//			Permission readUserAdmin = Permission.builder()
//					.name(PermissionEnum.READ_USER_ADMIN).build();  // Added READ_USER_ADMIN
//
//			Permission updateUser = Permission.builder()
//					.name(PermissionEnum.UPDATE_USER).build();
//
//			Permission deleteUser = Permission.builder()
//					.name(PermissionEnum.DELETE_USER).build();
//
//			Permission createOrder = Permission.builder()
//					.name(PermissionEnum.CREATE_ORDER).build();
//
//			Permission readOrder = Permission.builder()
//					.name(PermissionEnum.READ_ORDER).build();
//
//			Permission deleteOrder = Permission.builder()
//					.name(PermissionEnum.DELETE_ORDER).build();
//
//			Permission createOrderDetails = Permission.builder()
//					.name(PermissionEnum.CREATE_ORDERDETAILS).build();
//
//			Permission readOrderDetails = Permission.builder()
//					.name(PermissionEnum.READ_ORDERDETAILS).build();
//
//			Permission deleteOrderDetails = Permission.builder()
//					.name(PermissionEnum.DELETE_ORDERDETAILS).build();
//
//			Permission createRole = Permission.builder()
//					.name(PermissionEnum.CREATE_ROLE).build();
//
//			Permission readRole = Permission.builder()
//					.name(PermissionEnum.READ_ROLE).build();
//
//			Permission updateRole = Permission.builder()
//					.name(PermissionEnum.UPDATE_ROLE).build();
//
//			Permission deleteRole = Permission.builder()
//					.name(PermissionEnum.DELETE_ROLE).build();
//
//			Permission createPermission = Permission.builder()
//					.name(PermissionEnum.CREATE_PERMISSION).build();
//
//			Permission readPermission = Permission.builder()
//					.name(PermissionEnum.READ_PERMISSION).build();
//
//			// Creating roles and assigning permissions
//			Role adminRole = Role.builder()
//					.role(RoleEnum.ADMIN)
//					.permissions(Set.of(createProducts, readProducts, updateProducts, deleteProducts,
//							createCategory, readCategory, updateCategory, deleteCategory,
//							createUserAdmin, createUser, readUserAdmin, updateUser, deleteUser, readOrder, deleteOrder,
//							readOrderDetails, deleteOrderDetails,
//							createRole, readRole, updateRole, deleteRole,
//							createPermission, readPermission, readUser))
//					.build();
//
//			Role customerRole = Role.builder()
//					.role(RoleEnum.CUSTOMER)
//					.permissions(Set.of(readProducts, readCategory, createUser, readUser, createOrder,
//							readOrder, createOrderDetails, readOrderDetails))
//					.build();
//
//			Role developerRole = Role.builder()
//					.role(RoleEnum.DEVELOPER)
//					.permissions(Set.of(createProducts, readProducts, updateProducts, deleteProducts,
//							createCategory, readCategory, updateCategory, deleteCategory,
//							createUserAdmin, createUser, readUserAdmin, updateUser, deleteUser, readOrder, deleteOrder,
//							readOrderDetails, deleteOrderDetails,
//							createRole, readRole, updateRole, deleteRole,
//							createPermission, readPermission, readUser, createOrder, createOrderDetails))
//					.build();
//
//			//Creating users
//			UserEntity admin = UserEntity.builder().
//					username("admin").password(passwordEncoder.encode("123")).isEnabled(true).
//					accountNoExpired(true).accountNoLocked(true).credentialsNoExpired(true).
//					roles(Set.of(adminRole)).build();
//
//			UserEntity customer1 = UserEntity.builder().
//					username("juangar").password(passwordEncoder.encode("456")).isEnabled(true).
//					accountNoExpired(true).accountNoLocked(true).credentialsNoExpired(true).
//					roles(Set.of(customerRole)).build();
//
//			UserEntity customer2 = UserEntity.builder().
//					username("Alechava").password(passwordEncoder.encode("789")).isEnabled(true).
//					accountNoExpired(true).accountNoLocked(true).credentialsNoExpired(true).
//					roles(Set.of(customerRole)).build();
//
//			UserEntity developer = UserEntity.builder().
//					username("saianlord").password(passwordEncoder.encode("101112")).isEnabled(true).
//					accountNoExpired(true).accountNoLocked(true).credentialsNoExpired(true).
//					roles(Set.of(developerRole)).build();
//
//
//			service.saveAll(List.of(admin,customer1, customer2, developer));
//
//		};
//}
}

