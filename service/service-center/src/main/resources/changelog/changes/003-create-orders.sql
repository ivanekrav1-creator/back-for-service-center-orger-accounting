--liquibase formatted sql

--changeset ivan:003-create-orders
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    owner_fio VARCHAR(255) NOT NULL,
    owner_phone VARCHAR(30) NOT NULL,
    repair_cost NUMERIC(12, 2) NOT NULL,
    acceptance_date DATE NOT NULL,
    planned_issue_date DATE NOT NULL,
    issue_date DATE,
    status VARCHAR(30) NOT NULL,
    CONSTRAINT orders_repair_cost_check
        CHECK (repair_cost >= 0),
    CONSTRAINT orders_status_check
        CHECK (status IN ('IN_PROGRESS', 'COMPLETED')),
    CONSTRAINT orders_dates_check
        CHECK (
            planned_issue_date >= acceptance_date
            AND (
            issue_date IS NULL
                OR issue_date >= acceptance_date
                )
            )
);