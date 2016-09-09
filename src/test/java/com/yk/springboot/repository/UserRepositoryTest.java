package com.yk.springboot.repository;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;

import com.yk.springboot.SpringBootExampleApplicationTests;
import com.yk.springboot.entity.User;

public class UserRepositoryTest extends SpringBootExampleApplicationTests {

	@Autowired
	private UserRepository userRepository;
	
	User user1,user2,user3,user4,user5;
	
	@Before
	public void setup(){
		userRepository.deleteAll();
		
		this.user1 = userRepository.save(new User("15021470581","yk1","111111","111"));
		this.user2 = userRepository.save(new User("15021470582","yk2","222222","111"));
		this.user3 = userRepository.save(new User("15021470583","yk3","333333","111"));
		this.user4 = userRepository.save(new User("15021470584","yk4","444444","111"));
		this.user5 = userRepository.save(new User("15021470585","yk5","555555","111"));
	}
	
	@Test
	public void countBySimpleExample(){
	     Example<User> 	example = Example.of(new User(null,null,null,"111"));
	     Assert.assertThat(userRepository.count(example),Matchers.is(3L));
	}
}
