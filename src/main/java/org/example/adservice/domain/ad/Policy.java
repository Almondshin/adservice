package org.example.adservice.domain.ad;

import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.user.UserId;
import org.example.common.domain.DomainEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Policy extends DomainEntity<Policy, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "POLICY_TYPE", nullable = false)
    private PolicyType policyType;

    @Column(name = "POLICY_VALUE")
    private Integer policyValue;

    @Override
    public Long getId() {
        return id;
    }

    protected Policy() {}

    public Policy(PolicyType policyType, Integer policyValue) {
        this.policyType = policyType;
        this.policyValue = policyValue;
    }

    public boolean isSatisfiedBy(UserId userId, List<AdJoinHistory> historyList) {
        switch (this.policyType) {
            case ALREADY_JOINED_AD:
                return historyList.stream().anyMatch(e -> e.getId().getUserId().equals(userId));
            case MINIMUM_JOIN:
                return historyList.size() >= this.policyValue;
            case FIRST_TIME_ONLY:
                return historyList.isEmpty();
            default:
                return true;
        }
    }


}

