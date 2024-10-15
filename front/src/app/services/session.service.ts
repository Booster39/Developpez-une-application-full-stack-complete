import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable } from 'rxjs';
import { AuthService } from '../features/auth/services/auth.service';
import { User } from '../interfaces/user.interface';
import { Topic } from '../interfaces/topic.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  public isLogged = false;
  public user: User | undefined;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  
  // Nouveau BehaviorSubject pour les sujets suivis
  private followedTopicsSubject = new BehaviorSubject<Topic[]>([]);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  // Observable pour suivre la liste des sujets abonnés
  public $followedTopics(): Observable<Topic[]> {
    return this.followedTopicsSubject.asObservable();
  }

  public logIn(user: User): void {
    this.user = user;
    this.isLogged = true;
    this.followedTopicsSubject.next(user.followedTopics); // Mise à jour des sujets suivis
    this.next();
  }

  public logOut(): void {
    localStorage.removeItem('token');
    this.user = undefined;
    this.isLogged = false;
    this.followedTopicsSubject.next([]); // Vider les sujets suivis lors de la déconnexion
    this.next();
  }

  // Méthode pour mettre à jour les sujets suivis
  public updateFollowedTopics(topics: Topic[]): void {
    this.user!.followedTopics = topics;
    this.followedTopicsSubject.next(topics);
  }

  private next(): void {
    this.isLoggedSubject.next(this.isLogged);
  }
}