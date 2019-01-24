package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.dto.MenuDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.UserSecurityDto;
import net.cis.jpa.entity.UserSecurityEntity;
import net.cis.repository.UserSecurityRepository;
import net.cis.service.UserSecurityService;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {

	@Autowired
	private UserSecurityRepository userSecurityRepository;

	ModelMapper mapper;

	@PostConstruct
	public void initialize() {
		mapper = new ModelMapper();
	}

	@Override
	public ResponseApi save(UserSecurityDto userSecurityDto) {
		UserSecurityEntity userSecurity = new UserSecurityEntity();
		mapper.map(userSecurityDto, userSecurity);
		ResponseApi api = new ResponseApi();
		return api;
	}

	@Override
	public UserSecurityDto findByUsername(String username) {
		UserSecurityDto dto = new UserSecurityDto();
		UserSecurityEntity entity = userSecurityRepository.findByUsername(username);

		if (entity == null) {
			return null;
		}
		mapper.map(entity, dto);
		return dto;
	}

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<MenuDto> getMenuByRole(Integer roleId) {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_menu");
		storedProcedureQuery.registerStoredProcedureParameter("role", Integer.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("role", roleId);
		storedProcedureQuery.execute();
		List<MenuDto> result = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> lst = storedProcedureQuery.getResultList();
		Integer temp_parent = 0;
		int start = 0;
		MenuDto objMenuDto = new MenuDto();
		List<MenuDto> menuChilds = new ArrayList<MenuDto>();
		for (Object[] value : lst) {
			if (temp_parent != (int) value[0]) {
				if (start != 0) {
					objMenuDto.setMenuChilds(menuChilds);
					result.add(objMenuDto);
				}
				objMenuDto = new MenuDto();
				menuChilds = new ArrayList<MenuDto>();
			} else {
				objMenuDto.setName(value[1].toString());
				objMenuDto.setLabel(value[2].toString());
				objMenuDto.setDescription(value[3].toString());
				objMenuDto.setLevel((int) value[4]);

				MenuDto menuChild = new MenuDto();
				menuChild.setLabel(value[6].toString());
				menuChild.setDescription(value[7].toString());
				menuChild.setLevel((int) value[8]);
				menuChilds.add(menuChild);
			}
			start++;
		}
		return result;
	}
}
