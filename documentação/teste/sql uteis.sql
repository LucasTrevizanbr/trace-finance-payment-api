
#faz o select de uma carteira pelo seu UUID
SELECT * FROM wallet WHERE id = UUID_TO_BIN('a506d681-529e-4f84-b1d3-60183d3d1f30');

#Select utilizado para calcular o montante de pagamentos já feito a uma carteira no dia de acordo com um periodo
SELECT SUM(p.amount)
FROM payment p 
WHERE p.wallet_id = UUID_TO_BIN('a506d681-529e-4f84-b1d3-60183d3d1f30')
AND p.period LIKE 'NIGHTLY' 
AND cast(p.payment_date_time AS DATE) = '2022-06-30'