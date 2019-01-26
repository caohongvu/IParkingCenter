package net.cis.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.cis.constants.UserConstans;
import net.cis.dto.MenuDto;
import net.cis.dto.ResponseApi;
import net.cis.dto.UserSecurityDto;
import net.cis.jpa.entity.UserSecurityEntity;
import net.cis.repository.UserSecurityRepository;
import net.cis.service.UserSecurityService;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
	protected final Logger LOGGER = Logger.getLogger(getClass());
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
	public List<MenuDto> getMenuByRoleForWeb(Integer roleId) {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_menu");
		storedProcedureQuery.registerStoredProcedureParameter("role", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("type_func", Integer.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("role", roleId);
		storedProcedureQuery.setParameter("type_func", UserConstans.FUNC_TYPE_WEB);
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
				objMenuDto.setId((int) value[0]);
				objMenuDto.setName(value[1] != null ? value[1].toString() : null);
				objMenuDto.setLabel(value[2] != null ? value[2].toString() : null);
				objMenuDto.setDescription(value[3] != null ? value[3].toString() : null);
				objMenuDto.setLevel((int) value[4]);

				MenuDto menuChild = new MenuDto();
				menuChild.setId((int) value[5]);
				menuChild.setName(value[6] != null ? value[6].toString() : null);
				menuChild.setLabel(value[7] != null ? value[7].toString() : null);
				menuChild.setDescription(value[8] != null ? value[8].toString() : null);
				menuChild.setLevel((int) value[9]);
				menuChild.setParent_id((int) value[0]);

				menuChild.setIcon(value[10] != null ? value[10].toString() : null);
				menuChild.setLink(value[11] != null ? value[11].toString() : null);

				menuChilds.add(menuChild);
			}
			if (start == lst.size() - 1) {
				objMenuDto.setMenuChilds(menuChilds);
				result.add(objMenuDto);
			}
			start++;
			temp_parent = (int) value[0];
		}
		return result;
	}

	@Override
	public List<MenuDto> getMenuByRoleForApp(Integer roleId) {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("sp_menu");
		storedProcedureQuery.registerStoredProcedureParameter("role", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("type_func", Integer.class, ParameterMode.IN);
		storedProcedureQuery.setParameter("role", roleId);
		storedProcedureQuery.setParameter("type_func", UserConstans.FUNC_TYPE_APP);
		storedProcedureQuery.execute();
		List<MenuDto> result = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Object[]> lst = storedProcedureQuery.getResultList();
		for (Object[] value : lst) {
			if ((int) value[9] == UserConstans.FUNC_LEVEL_2) {
				MenuDto objMenuDto = new MenuDto();
				objMenuDto.setId((int) value[5]);
				objMenuDto.setName(value[6] != null ? value[6].toString() : null);
				objMenuDto.setLabel(value[7] != null ? value[7].toString() : null);
				objMenuDto.setDescription(value[8] != null ? value[8].toString() : null);
				objMenuDto.setLevel((int) value[9]);
				objMenuDto.setParent_id((int) value[0]);
				result.add(objMenuDto);
			}
		}
		return result;
	}
}
