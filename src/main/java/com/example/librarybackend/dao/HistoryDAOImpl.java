package com.example.librarybackend.dao;

import com.example.librarybackend.CustomException;
import com.example.librarybackend.dto.*;
import com.example.librarybackend.entity.History;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class HistoryDAOImpl implements HistoryDAO{
    EntityManager entityManager;

    public HistoryDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public History findById(long id) {

        try {
            return entityManager
                    .createQuery("FROM History WHERE id= :id", History.class)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public History save(History history) {
        History validateHistory = findById(history.getId());
        if(validateHistory != null) {
            throw new CustomException("History already existed");
        }
        entityManager.persist(history);
        return history;
    }

    @Override
    public PaginationHistoriesDTO findHistoryByEmail(String userEmail, int pageNo, int pageSize) {
        TypedQuery<History> query = entityManager
                .createQuery("FROM History WHERE userEmail=:userEmail", History.class)
                .setParameter("userEmail", userEmail);

        if(pageSize > 0) {
            query.setMaxResults(pageSize);
            if(pageNo > 0) {
                query.setFirstResult((pageNo - 1) * pageSize);
            }
        }
        List<History> histories = query.getResultList();

        TypedQuery<Long> countQuery = entityManager
                .createQuery("SELECT COUNT(*) FROM History WHERE userEmail=:userEmail", Long.class)
                .setParameter("userEmail", userEmail);
        long totalOfHistory = countQuery.getSingleResult();
        List<HistoryDTO> historyDTOs = new ArrayList<>();
        for (History history: histories) {
            historyDTOs.add(new HistoryDTO(history));
        }

        int totalPages = (pageSize > 0) ? (int)Math.ceil((double) totalOfHistory / pageSize) : 1;

        Pagination pagination = new Pagination(totalOfHistory, pageNo, totalPages);
        return new PaginationHistoriesDTO(historyDTOs, pagination);
    }
}
