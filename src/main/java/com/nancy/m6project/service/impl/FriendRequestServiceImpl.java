package com.nancy.m6project.service.impl;

import com.nancy.m6project.model.friendRequest.FriendRequest;
import com.nancy.m6project.repositories.friendRequest.FriendRequestRepositories;
import com.nancy.m6project.service.friendRequest.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    @Autowired
    private FriendRequestRepositories friendRequestRepositories;

    @Override
    public List<FriendRequest> getAllFriendRequestAccountReceived(Long accountId, Integer status) {
        return friendRequestRepositories.findAllByAccountReceiveIdAndStatus(accountId, status);
    }

    @Override
    public List<FriendRequest> findAll() {
        return null;
    }

    @Override
    public List<FriendRequest> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<FriendRequest> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public FriendRequest findById(Long id) {
        return friendRequestRepositories.findById(id).get();
    }

    @Override
    public FriendRequest save(FriendRequest model) {
        return friendRequestRepositories.save(model);
    }

    @Override
    public FriendRequest update(FriendRequest model) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        friendRequestRepositories.deleteById(id);
        FriendRequest friendRequest = friendRequestRepositories.findById(id).orElse(null);
        return friendRequest == null;
    }

    @Override
    public Boolean existsByAccountSendIdOrAccountReceiveId(Long accountSent, Long accountReceive) {
        return friendRequestRepositories.existsByAccountSendIdOrAccountReceiveId(accountSent, accountReceive);
    }

    @Override
    public Boolean existsFriendRequestByAccountReceive_IdAndAccountSend_Id(Long sentId, Long receiveId) {
        return friendRequestRepositories.existsFriendRequestByAccountReceive_IdAndAccountSend_Id(sentId, receiveId);
    }

    @Override
    public List<FriendRequest> getAllFriendByAccountId(Long accountSendId, Integer status1, Long accountReceiveId, Integer status2) {
        return friendRequestRepositories.findAllByAccountSendIdAndStatusOrAccountReceiveIdAndStatus(accountSendId, status1, accountReceiveId, status2);
    }

    @Override
    public FriendRequest getFriendRequestByAccountSendIdAndAccountReceiveId(Long accountSendId, Long accountReceiveId) {
        return friendRequestRepositories.findFriendRequestByAccountSendIdAndAccountReceiveId(accountSendId, accountReceiveId);
    }

    @Override
    public String checkRelation(Long currentId, Long checkId) {
        FriendRequest friendRequest = friendRequestRepositories.findFriendRequestByAccountSendIdAndAccountReceiveId(currentId, checkId);
        FriendRequest friendRequestReverse = friendRequestRepositories.findFriendRequestByAccountSendIdAndAccountReceiveId(checkId, currentId);
        String relation = "";
        if (friendRequest == null && friendRequestReverse == null) {
            relation = "none";
        }
        if (friendRequest == null && friendRequestReverse != null) {
            if (friendRequestReverse.getStatus() == 0) {
                relation = "pending";
            }
            if (friendRequestReverse.getStatus() == 1) {
                relation = "friend";
            }
        }
        if (friendRequest != null && friendRequestReverse == null) {
            if (friendRequest.getStatus() == 0) {
                relation = "pending";
            }
            if (friendRequest.getStatus() == 1) {
                relation = "friend";
            }
        }
        return relation;
    }

    @Override
    public boolean checkHaveFriend(Long accountId) {
        List<FriendRequest> friendRequestList = friendRequestRepositories.findAllByAccountSendIdAndStatusOrAccountReceiveIdAndStatus(accountId,1, accountId, 1);
        if (friendRequestList.size() != 0) {
            return true;
        } else {
            return false;
        }
    }
}
