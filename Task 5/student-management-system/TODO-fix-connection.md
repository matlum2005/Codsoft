# Fix ConnectException: JavaFX Frontend to Spring Boot Backend

## Steps:
- [ ] 1. Verify backend status (port 8080)
- [ ] 2. Start backend: cd student-management-system/backend && mvn spring-boot:run
- [ ] 3. Update HttpService.java: Add timeout, logging, ConnectException handling
- [ ] 4. Test API endpoint: curl http://localhost:8080/api/students
- [ ] 5. Update controllers for user-friendly errors if needed
- [ ] 6. Build and run frontend: cd student-management-system/frontend-javafx && mvn javafx:run
- [ ] 7. Verify no ConnectException, successful connection
- [x] Plan approved
