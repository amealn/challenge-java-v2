kind create cluster --name kind
kind load docker-image challenge-java-v2:latest --name kind
kubectl apply -f k8s/
kubectl port-forward service/app-service 8080:2911



#optional
kind get clusters

kubectl get pods

kubectl logs <your-app-pod-name>
