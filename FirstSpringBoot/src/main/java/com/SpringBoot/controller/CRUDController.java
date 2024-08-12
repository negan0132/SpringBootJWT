package com.SpringBoot.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBoot.Mappers.PermissionMapper;
import com.SpringBoot.Mappers.RoleMapper;
import com.SpringBoot.Mappers.UserMapper;
import com.SpringBoot.dto.request.ClassRequest;
import com.SpringBoot.dto.request.PermissionRequest;
import com.SpringBoot.dto.request.RoleRequest;
import com.SpringBoot.dto.request.StudentRequest;
import com.SpringBoot.dto.request.TokenRequest;
import com.SpringBoot.dto.request.UserRequest;
import com.SpringBoot.dto.response.LoginResponse;
import com.SpringBoot.dto.response.PermissionResponse;
import com.SpringBoot.dto.response.RoleReponse;
import com.SpringBoot.dto.response.UserResponse;
import com.SpringBoot.entities.Class;
import com.SpringBoot.entities.Khoa;
import com.SpringBoot.entities.Permission;
import com.SpringBoot.entities.RefreshToken;
import com.SpringBoot.entities.Role;
import com.SpringBoot.entities.Student;
import com.SpringBoot.entities.Users;
import com.SpringBoot.repositories.RefreshTokenRepo;
import com.SpringBoot.services.ClassServiceIF;
import com.SpringBoot.services.KhoaServiceIF;
import com.SpringBoot.services.PermissionServiceIF;
import com.SpringBoot.services.RoleServiceIF;
import com.SpringBoot.services.StudentServiceIF;
import com.SpringBoot.services.UserServiceIF;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CRUDController {

	StudentServiceIF studentService;

	KhoaServiceIF khoaService;

	ClassServiceIF classService;

	PermissionServiceIF permissionService;

	PermissionMapper permissionMapper;

	RoleServiceIF roleService;

	RoleMapper roleMapper;

	UserServiceIF userService;

	UserMapper userMapper;
	
	RefreshTokenRepo refreshTokenRepo;

	@GetMapping("/student/all")
	public Optional<List<Student>> getAllStudent() {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		log.info("Name: " + authen.getName());
		log.info("Authorize: " + authen.getAuthorities());
		return studentService.getAllStudent();
	}

	@GetMapping("/student_by/{id}")
	public Optional<Student> getOneStudentByStudentID(@PathVariable Long id) {
		return studentService.getOneStudentById(id);
	}

	@PostMapping("/student/add")
	public ResponseEntity<?> addOneStudent(@RequestBody StudentRequest student) {
		if (student == null) {
			return new ResponseEntity<String>("Invalid Data", HttpStatus.BAD_REQUEST);
		}
		Optional<Khoa> khoaOp = khoaService.getOne(student.getKhoa_id());
		if (!khoaOp.isPresent()) {
			return new ResponseEntity<String>("Invalid Khoa ID", HttpStatus.BAD_REQUEST);
		}
		Student newStudent = new Student(0, student.getName(), student.getBirthday(), 2, khoaOp.get(), null);
		return new ResponseEntity<Optional<Student>>(studentService.saveOneStudent(newStudent), HttpStatus.OK);
	}

	@GetMapping("/student/{khoa_id}")
	public Optional<List<Student>> getStudentByKhoaid(@PathVariable Long khoa_id) {
		return studentService.getStudentByKhoa(khoa_id);
	}

	@PutMapping("/student/{id}")
	@PreAuthorize("hasAuthority('UPDATE_STUDENT')")
	public ResponseEntity<?> updateStudent(@PathVariable("id") long id, @RequestBody StudentRequest std) {
		Optional<Student> studentOp = studentService.getOneStudentById(id);
		Optional<Khoa> khoaOp = khoaService.getOne(std.getKhoa_id());
		if (studentOp.isPresent() && khoaOp.isPresent()) {
			Student studentAfter = studentOp.get();
			studentAfter.setBirthday(std.getBirthday());
			studentAfter.setGender(std.getGender());
			studentAfter.setName(std.getName());
			studentAfter.setKhoa(khoaOp.get());
			return new ResponseEntity<Optional<Student>>(studentService.saveOneStudent(studentAfter), HttpStatus.OK);
		}
		return new ResponseEntity<String>("Invalid Data", HttpStatus.BAD_REQUEST);
	}

	@DeleteMapping("/student/{id}")
	@PreAuthorize("hasAuthority('DELETE_STUDENT')")
	public ResponseEntity<?> deleteStudent(@PathVariable("id") long id) {
		Optional<Student> studentOp = studentService.getOneStudentById(id);
		if (studentOp.isPresent()) {
			studentService.deleteOne(studentOp.get());
			return new ResponseEntity<String>("Delete Success", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Invalid data", HttpStatus.BAD_REQUEST);
	}

	/**
	 * Controller của Lớp
	 */
	@PostMapping("/class/add")
	public ResponseEntity<?> createOneClass(@RequestBody @Valid ClassRequest classRequest) {
		Class rootClass = new Class("", classRequest.getTenLop(), new HashSet<Student>(), null);
		List<Long> list_studentID = classRequest.getListStudent();
		for (int i = 0; i < list_studentID.size(); i++) {
			Optional<Student> studentOp = studentService.getOneStudentById(list_studentID.get(i));
			if (studentOp.isPresent()) {
				rootClass.getListSV().add(studentOp.get());
			}
		}
		Optional<Khoa> khoaOp = khoaService.getOne(classRequest.getKhoa());
		if (khoaOp.isPresent()) {
			rootClass.setKhoa(khoaOp.get());
		}
		if (classService.saveOne(rootClass).isPresent()) {
			return new ResponseEntity<String>("Insert Sucess", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Insert fail", HttpStatus.BAD_REQUEST);
	}

	/*
	 * CRUD handle Permission
	 */
	@PostMapping("/permission/add")
	public ResponseEntity<?> createOnePermission(@RequestBody PermissionRequest request) {
		Optional<Permission> opPer = permissionService.saveOne(permissionMapper.toEntity(request));
		if (opPer.isPresent()) {
			return new ResponseEntity<PermissionResponse>(permissionMapper.toPermissionResponse(opPer.get()),
					HttpStatus.OK);
		}
		return new ResponseEntity<String>("Insert fail", HttpStatus.BAD_REQUEST);
	}

	/*
	 * CRUD handle Role
	 */
	@PostMapping("/role/add")
	public ResponseEntity<?> createOneRole(@RequestBody RoleRequest request) {
		Role role = roleMapper.toEntity(request);
		List<Permission> perSet = permissionService.getAllPerById(request.getPermissions()).get();
		role.setPermissions(new HashSet<Permission>(perSet));
		Optional<Role> opRole = roleService.createOne(role);
		if (opRole.isPresent()) {
			return new ResponseEntity<RoleReponse>(roleMapper.toRoleResponse(opRole.get()), HttpStatus.OK);
		}
		return new ResponseEntity<String>("Insert fail", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/role/all")
	public ResponseEntity<?> getAllRole() {
		List<Role> listRole = roleService.getAllRole();
		return ResponseEntity.ok(roleMapper.toListRoleResponse(listRole));
	}

	/**
	 * CRUD handle User
	 */

	@PostMapping("/user/add")
	public ResponseEntity<?> createOneUser(@RequestBody UserRequest request) {
		Users user = userMapper.toEntity(request);
		List<Role> roleList = roleService.getAllByRole_Name(request.getRoles()).get();
		user.setRoles(new HashSet<Role>(roleList));
		PasswordEncoder pe = new BCryptPasswordEncoder(10);
		user.setPassword(pe.encode(request.getPassword()));
		Optional<Users> opUser = userService.createOne(user);
		if (opUser.isPresent()) {
			return new ResponseEntity<UserResponse>(userMapper.toUserResponse(opUser.get()), HttpStatus.OK);
		}
		return new ResponseEntity<String>("Insert fail", HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/user/{username}")
	@PostAuthorize("returnObject.username == authentication.name or hasRole('ROLE_ADMIN')")
	public Users getOneUserByUsername(@PathVariable String username) {
		return userService.getByUserName(username);
	}

	/*
	 * Require Access Token	
	 */
	@GetMapping("/refresh-token")
	public LoginResponse refreshTokenNow(@RequestBody TokenRequest refreshId) {
		RefreshToken root = refreshTokenRepo.findById(refreshId.getToken()).orElseThrow(() -> new RuntimeException("No Refresh Token with this ID"));
		if(root.getExpirationDate().before(new Date())) {
			throw new RuntimeException("You have to login again");
		}
		return new LoginResponse().builder()
				.authenticated(true)
				.access_token(userService.getUserToken(root.getUser()))
				.refresh_token(refreshId.getToken())
				.build();
	}
	
}
