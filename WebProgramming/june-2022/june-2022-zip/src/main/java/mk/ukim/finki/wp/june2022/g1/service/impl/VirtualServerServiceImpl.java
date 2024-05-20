package mk.ukim.finki.wp.june2022.g1.service.impl;

import mk.ukim.finki.wp.june2022.g1.model.OSType;
import mk.ukim.finki.wp.june2022.g1.model.User;
import mk.ukim.finki.wp.june2022.g1.model.VirtualServer;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidUserIdException;
import mk.ukim.finki.wp.june2022.g1.model.exceptions.InvalidVirtualMachineIdException;
import mk.ukim.finki.wp.june2022.g1.repository.UserRepository;
import mk.ukim.finki.wp.june2022.g1.repository.VirtualServerRepository;
import mk.ukim.finki.wp.june2022.g1.service.UserService;
import mk.ukim.finki.wp.june2022.g1.service.VirtualServerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirtualServerServiceImpl  implements VirtualServerService {
    private final VirtualServerRepository serverRepository;
    private final UserService userService;

    private final UserRepository userRepository;



    public VirtualServerServiceImpl(VirtualServerRepository serverRepository, UserService userService, UserRepository userRepository) {
        this.serverRepository = serverRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public List<VirtualServer> listAll() {
        return this.serverRepository.findAll();
    }

    @Override
    public VirtualServer findById(Long id) {
        return this.serverRepository.findById(id).orElseThrow(InvalidVirtualMachineIdException::new);
    }

    @Override
    public VirtualServer create(String name, String ipAddress, OSType osType, List<Long> owners, LocalDate launchDate) {
        List<User> users=owners
                .stream()
                .map(this.userService::findById)
                .collect(Collectors.toList());
        VirtualServer virtualServer=new VirtualServer(name,ipAddress,osType,users,launchDate);

        return this.serverRepository.save(virtualServer);
    }

    @Override
    public VirtualServer update(Long id, String name, String ipAddress, OSType osType, List<Long> owners) {

        VirtualServer virtualServer=findById(id);
        virtualServer.setInstanceName(name);
        virtualServer.setIpAddress(ipAddress);
        virtualServer.setOSType(osType);
        List<User> users=this.userRepository.findAllById(owners);
        virtualServer.setOwners(users);

        return this.serverRepository.save(virtualServer);
    }

    @Override
    public VirtualServer delete(Long id) {

        VirtualServer virtualServer=findById(id);
        this.serverRepository.delete(virtualServer);
        return virtualServer;
    }

    @Override
    public VirtualServer markTerminated(Long id) {

        VirtualServer virtualServer=findById(id);
        virtualServer.setTerminated(true);
        this.serverRepository.save(virtualServer);
        return virtualServer;

    }

    public List<VirtualServer> filter(Long ownerId, Integer activeMoreThanDays) {
        if(ownerId != null && activeMoreThanDays != null) {
            return this.serverRepository.findAllByOwnersAndLaunchDateBefore(
                    userService.findById(ownerId),
                    LocalDate.now().minusDays((long) activeMoreThanDays));
        }
        else if(ownerId==null && activeMoreThanDays!=null){
            return this.serverRepository.findAllByLaunchDateBefore(
                    LocalDate.now().minusDays((long) activeMoreThanDays));
        }
        else if(ownerId!=null && activeMoreThanDays==null) {
            return this.serverRepository.findAllByOwners(userService.findById(ownerId));
        }
        else {
            return this.serverRepository.findAll();
        }
    }
}
