import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CoursesComponent } from './conteiners/courses/courses.component';
import { CoursesFormComponent } from './conteiners/courses-form/courses-form.component';
import { CourseResolver } from './guards/course.resolver';

const routes: Routes = [
  { path: '', component: CoursesComponent },
  { path: 'new', component: CoursesFormComponent, resolve: { course: CourseResolver } },
  { path: 'edit/:id', component: CoursesFormComponent, resolve: { course: CourseResolver } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoursesRoutingModule {}
